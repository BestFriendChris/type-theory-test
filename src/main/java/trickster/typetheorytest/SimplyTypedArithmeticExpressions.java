package trickster.typetheorytest;

import trickster.typetheorytest.data.DataConstructor;
import trickster.typetheorytest.data.DataType;
import trickster.typetheorytest.data.Tuple;

import static trickster.typetheorytest.match.MatchTest.orElse;
import static trickster.typetheorytest.match.MatchTest.when;
import static trickster.typetheorytest.match.MatchTest.whenAny;
import static trickster.typetheorytest.match.Matcher.match;

/*
  From Chapter 4 of Types and Programming Languages (Benjamin C. Pierce 2002)
 */
public class SimplyTypedArithmeticExpressions {

    /* OCaml: (Not currently dealing with the info argument)
            type term =
                  TmTrue of info
                | TmFalse of info
                | TmIf of info * term * term * term
                | TmZero of info
                | TmSucc of info * term
                | TmPred of info * term
                | TmIsZero of info * term
     */
    public static class Term extends DataType<Term> {
        public static final DataConstructor.c0<Term>
                TmTrue = new DataConstructor.c0<>("TmTrue", Term::new);
        public static final DataConstructor.c0<Term>
                TmFalse = new DataConstructor.c0<>("TmFalse", Term::new);
        public static final DataConstructor.c3<Term, Term, Term, Term>
                TmIf = new DataConstructor.c3<>("TmIf", Term::new);
        public static final DataConstructor.c0<Term>
                TmZero = new DataConstructor.c0<>("TmZero", Term::new);
        public static final DataConstructor.c1<Term, Term>
                TmSucc = new DataConstructor.c1<>("TmSucc", Term::new);
        public static final DataConstructor.c1<Term, Term>
                TmPred = new DataConstructor.c1<>("TmPred", Term::new);
        public static final DataConstructor.c1<Term, Term>
                TmIsZero = new DataConstructor.c1<>("TmIsZero", Term::new);

        public Term(DataConstructor constructor, Tuple value) {
            super("Term", constructor, value);
        }
    }

    /* OCaml:
            let rec isnumericval t = match t with TmZero(_) → true
                | TmSucc(_,t1) → isnumericval t1
                | _ → false
     */
    public static Boolean isNumericVal(Term term) {
        return match(term).on(
                when(Term.TmZero) .then(true),
                when(Term.TmSucc) .then(t ->  SimplyTypedArithmeticExpressions.isNumericVal(t)),
                orElse(false)
        );
    }

    /* OCaml:
            let rec isval t = match t with
                TmTrue(_) → true
                | TmFalse(_) → true
                | t when isnumericval t → true
                | _ → false
     */
    public static Boolean isVal(Term term) {
        return match(term).on(
                when(Term.TmTrue)
                    .then(true),
                when(Term.TmFalse)
                    .then(true),
                whenAny(() -> isNumericVal(term))
                    .then(true),
                orElse(false)
        );
    }

    /* OCaml:
            exception NoRuleApplies
     */
    public static class NoRuleApplies extends RuntimeException {
    }

    /* OCaml:
            let rec eval1 t = match t with
                TmIf(_,TmTrue(_),t2,t3) →
                    t2
                | TmIf(_,TmFalse(_),t2,t3) →
                    t3
                | TmIf(fi,t1,t2,t3) →
                    let t1’ = eval1 t1 in
                    TmIf(fi, t1’, t2, t3)
                | TmSucc(fi,t1) →
                    let t1’ = eval1 t1 in
                    TmSucc(fi, t1’)
                | TmPred(_,TmZero(_)) →
                    TmZero(dummyinfo)
                | TmPred(_,TmSucc(_,nv1)) when (isnumericval nv1) →
                    nv1
                | TmPred(fi,t1) →
                    let t1’ = eval1 t1 in
                    TmPred(fi, t1’)
                | TmIsZero(_,TmZero(_)) →
                    TmTrue(dummyinfo)
                | TmIsZero(_,TmSucc(_,nv1)) when (isnumericval nv1) →
                    TmFalse(dummyinfo)
                | TmIsZero(fi,t1) →
                    let t1’ = eval1 t1 in
                    TmIsZero(fi, t1’)
                | _ →
                    raise NoRuleApplies
     */
    public static Term eval1(Term term) throws NoRuleApplies {
        return match(term).on(
                when(Term.TmIf).and((test, _t, _f) -> test.matches(Term.TmTrue))
                        .then((_t, trueCase, falseCase) -> trueCase),
                when(Term.TmIf).and((test, _t, _f) -> test.matches(Term.TmFalse))
                        .then((_t, trueCase, falseCase) -> falseCase),
                when(Term.TmIf).then((test, trueCase, falseCase) -> {
                    return Term.TmIf.create(eval1(test), trueCase, falseCase);
                }),
                when(Term.TmSucc)
                        .then(t1 -> Term.TmSucc.create(eval1(t1))),
                when(Term.TmPred).and(t1 -> t1.matches(Term.TmZero))
                        .then(t1 -> Term.TmZero.create()),
                when(Term.TmPred)
                        .and(t1 -> {
                            return match(t1).on(
                                when(Term.TmSucc).then(SimplyTypedArithmeticExpressions::isNumericVal),
                                orElse(false)
                            );
                        })
                        .then(t1 -> {
                            return match(t1).on(
                                    when(Term.TmSucc).then(nv1 -> nv1)
                            );
                        }),
                when(Term.TmPred)
                        .then(t1 -> Term.TmPred.create(eval1(t1))),
                when(Term.TmIsZero).and(t1 -> t1.matches(Term.TmZero))
                        .then(t1 -> Term.TmTrue.create()),
                when(Term.TmIsZero)
                        .and(t1 -> {
                            return match(t1).on(
                                    when(Term.TmSucc).then(SimplyTypedArithmeticExpressions::isNumericVal),
                                    orElse(false)
                            );
                        })
                        .then(t1 -> Term.TmFalse.create()),
                when(Term.TmIsZero)
                        .then(t1 -> Term.TmIsZero.create(eval1(t1))),
                orElse(() -> { throw new NoRuleApplies(); })
        );
    }

    /* OCaml:
            let rec eval t =
                try let t’ = eval1 t
                    in eval t’
                with NoRuleApplies → t
     */
    public static Term eval(Term term) {
        Term evaluated = term;
        try {
            while(true) {
                evaluated = eval1(evaluated);
            }
        } catch (NoRuleApplies e) {
            return evaluated;
        }
    }

}

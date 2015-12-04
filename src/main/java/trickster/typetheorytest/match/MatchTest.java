package trickster.typetheorytest.match;

import trickster.typetheorytest.functions.Function0;
import trickster.typetheorytest.functions.Function1;
import trickster.typetheorytest.functions.Function2;
import trickster.typetheorytest.functions.Function3;
import trickster.typetheorytest.data.DataConstructor;
import trickster.typetheorytest.data.DataType;

import java.util.Optional;
import java.util.function.Supplier;

public class MatchTest<TVal, TData extends DataType<TData>> {
    private final Function1<Optional<TVal>, TData> test;

    private MatchTest(Function1<Optional<TVal>, TData> test) {
        this.test = test;
    }

    public Optional<TVal> matches(TData dataType) {
        return test.apply(dataType);
    }

    public static <TVal, TData extends DataType<TData>> MatchTest<TVal, TData> orElse(Supplier<TVal> fn) {
        return new MatchTest<>(tData -> Optional.ofNullable(fn.get()));
    }

    public static <TVal, TData extends DataType<TData>> MatchTest<TVal, TData> orElse(TVal val) {
        return new MatchTest<>(tData -> Optional.ofNullable(val));
    }

    public static untyped whenAny(Supplier<Boolean> test) {
        return new untyped(test);
    }

    public static class untyped<TData extends DataType<TData>> {

        private final Supplier<Boolean> test;

        private untyped(Supplier<Boolean> test) {
            this.test = test;
        }

        public untyped<TData> and(Supplier<Boolean> test) {
            final Supplier<Boolean> originalTest = this.test;
            return new untyped<>(() -> originalTest.get() && test.get());
        }

        public <TVal> MatchTest<TVal, TData> then(Function0<TVal> fn) {
            return new MatchTest<>(
                    dataType -> {
                        if (test.get()) {
                            return Optional.of(fn.apply());
                        } else {
                            return Optional.empty();
                        }
                    }
            );
        }

        public <TVal> MatchTest<TVal, TData> then(TVal val) {
            return then(() -> val);
        }
    }

    public static <TData extends DataType<TData>> t0<TData> when(DataConstructor.c0<TData> c) {
        return new t0<>(c);
    }

    public static class t0<TData extends DataType<TData>> {
        private final DataConstructor.c0<TData> testConstructor;
        private final Function0<Boolean> test;

        private t0(DataConstructor.c0<TData> testConstructor) {
            this(testConstructor, () -> true);
        }

        private t0(DataConstructor.c0<TData> testConstructor, Function0<Boolean> test) {
            this.testConstructor = testConstructor;
            this.test = test;
        }

        public t0<TData> and(Function0<Boolean> test) {
            final Function0<Boolean> originalTest = this.test;
            return new t0<>(testConstructor, () -> originalTest.apply() && test.apply());
        }

        public <TVal> MatchTest<TVal, TData> then(Function0<TVal> fn) {
            return new MatchTest<>(
                    dataType -> dataType.value(testConstructor)
                            .filter(value -> test.apply())
                            .map(value -> fn.apply())
            );
        }

        public <TVal> MatchTest<TVal, TData> then(TVal val) {
            return then(() -> val);
        }
    }

    public static <TData extends DataType<TData>, A> t1<TData, A> when(DataConstructor.c1<TData, A> c) {
        return new t1<>(c);
    }

    public static class t1<TData extends DataType<TData>, A> {
        private final DataConstructor.c1<TData, A> testConstructor;
        private final Function1<Boolean, A> test;

        private t1(DataConstructor.c1<TData, A> c) {
            this(c, a -> true);
        }

        private t1(DataConstructor.c1<TData, A> testConstructor, Function1<Boolean, A> test) {
            this.testConstructor = testConstructor;
            this.test = test;
        }

        public t1<TData, A> and(Function1<Boolean, A> test) {
            Function1<Boolean, A> originalTest = this.test;
            return new t1<>(testConstructor, a -> originalTest.apply(a) && test.apply(a));
        }

        public t1<TData, A> and(Supplier<Boolean> test) {
            return and(a -> test.get());
        }

        public <TVal> MatchTest<TVal, TData> then(Function1<TVal, A> fn) {
            return new MatchTest<>(
                    dataType -> dataType.value(testConstructor)
                            .filter(value -> test.apply(value.a))
                            .map(value -> fn.apply(value.a))
            );
        }

        public <TVal> MatchTest<TVal, TData> then(Supplier<TVal> fn) {
            return then(a -> fn.get());
        }

        public <TVal> MatchTest<TVal, TData> then(TVal val) {
            return then(a -> val);
        }
    }

    public static <TData extends DataType<TData>, A, B> t2<TData, A, B> when(DataConstructor.c2<TData, A, B> c) {
        return new t2<>(c);
    }

    public static class t2<TData extends DataType<TData>, A, B> {
        private final DataConstructor.c2<TData, A, B> testConstructor;
        private final Function2<Boolean, A, B> test;

        private t2(DataConstructor.c2<TData, A, B> c) {
            this(c, (a, b) -> true);
        }

        private t2(DataConstructor.c2<TData, A, B> testConstructor, Function2<Boolean, A, B> test) {
            this.testConstructor = testConstructor;
            this.test = test;
        }

        public t2<TData, A, B> and(Function2<Boolean, A, B> test) {
            final Function2<Boolean, A, B> originalTest = this.test;
            return new t2<>(this.testConstructor, (a, b) -> originalTest.apply(a, b) && test.apply(a, b));
        }

        public t2<TData, A, B> and(Supplier<Boolean> test) {
            return and((a, b) -> test.get());
        }

        public <TVal> MatchTest<TVal, TData> then(Function2<TVal, A, B> fn) {
            return new MatchTest<>(
                    dataType -> dataType.value(testConstructor)
                            .filter(value -> test.apply(value.a, value.b))
                            .map(value -> fn.apply(value.a, value.b))
            );
        }

        public <TVal> MatchTest<TVal, TData> then(Supplier<TVal> fn) {
            return then((a, b) -> fn.get());
        }

        public <TVal> MatchTest<TVal, TData> then(TVal val) {
            return then((a, b) -> val);
        }
    }

    public static <TData extends DataType<TData>, A, B, C> t3<TData, A, B, C> when(DataConstructor.c3<TData, A, B, C> c) {
        return new t3<>(c);
    }

    public static class t3<TData extends DataType<TData>, A, B, C> {
        private final DataConstructor.c3<TData, A, B, C> testConstructor;
        private final Function3<Boolean, A, B, C> test;

        private t3(DataConstructor.c3<TData, A, B, C> c) {
            this(c, (a, b, c1) -> true);
        }

        private t3(DataConstructor.c3<TData, A, B, C> testConstructor, Function3<Boolean, A, B, C> test) {
            this.testConstructor = testConstructor;
            this.test = test;
        }

        public t3<TData, A, B, C> and(Function3<Boolean, A, B, C> test) {
            final Function3<Boolean, A, B, C> originalTest = this.test;
            return new t3<>(testConstructor, (a, b, c) -> originalTest.apply(a, b, c) && test.apply(a, b, c));
        }

        public t3<TData, A, B, C> and(Supplier<Boolean> test) {
            return and((a1, b1, c1) -> test.get());
        }

        public <TVal> MatchTest<TVal, TData> then(Function3<TVal, A, B, C> fn) {
            return new MatchTest<>(
                    dataType -> dataType.value(testConstructor)
                            .filter(value -> test.apply(value.a, value.b, value.c))
                            .map(value -> fn.apply(value.a, value.b, value.c))
            );
        }

        public <TVal> MatchTest<TVal, TData> then(Supplier<TVal> fn) {
            return then((a, b, c) -> fn.get());
        }

        public <TVal> MatchTest<TVal, TData> then(TVal val) {
            return then((a, b, c) -> val);
        }
    }
}

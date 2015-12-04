package trickster.typetheorytest.data;

import trickster.typetheorytest.functions.Function2;

import java.util.Optional;

public abstract class DataConstructor<TData extends DataType<TData>, TCon extends DataConstructor<TData, TCon, TValue>, TValue extends Tuple> {
    private final String name;
    private final Function2<TData, TCon, TValue> dtConstructor;

    private DataConstructor(String name, Function2<TData, TCon, TValue> dtConstructor) {
        this.name = name;
        this.dtConstructor = dtConstructor;
    }

    public String name() {
        return name;
    }

    TData createDataType(TCon con, TValue value) {
        return dtConstructor.apply(con, value);
    }

    public Optional<TValue> tryCast(DataConstructor<?, ?, ?> constructor, Tuple tuple) {
        if (this == constructor) {
            @SuppressWarnings("unchecked")
            TValue castTuple = (TValue) tuple;
            return Optional.of(castTuple);
        } else {
            return Optional.empty();
        }
    }

    public static class c0<TData extends DataType<TData>> extends DataConstructor<TData, c0<TData>, Tuple.t0> {
        public c0(String name, Function2<TData, c0<TData>, Tuple> dtConstructor)  {
            super(name, dtConstructor::apply);
        }

        public TData create() {
            return createDataType(this, Tuple.UNIT);
        }
    }

    public static class c1<TData extends DataType<TData>, A> extends DataConstructor<TData, c1<TData, A>, Tuple.t1<A>> {
        public c1(String name, Function2<TData, c1<TData, A>, Tuple> dtConstructor)  {
            super(name, dtConstructor::apply);
        }

        public TData create(A a) {
            return createDataType(this, new Tuple.t1<>(a));
        }
    }

    public static class c2<TData extends DataType<TData>, A, B> extends DataConstructor<TData, c2<TData, A, B>, Tuple.t2<A, B>> {
        public c2(String name, Function2<TData, c2<TData, A, B>, Tuple> dtConstructor)  {
            super(name, dtConstructor::apply);
        }

        public TData create(A a, B b) {
            return createDataType(this, new Tuple.t2<>(a, b));
        }
    }

    public static class c3<TData extends DataType<TData>, A, B, C> extends DataConstructor<TData, c3<TData, A, B, C>, Tuple.t3<A, B, C>> {
        public c3(String name, Function2<TData, c3<TData, A, B, C>, Tuple> dtConstructor)  {
            super(name, dtConstructor::apply);
        }

        public TData create(A a, B b, C c) {
            return createDataType(this, new Tuple.t3<>(a, b, c));
        }
    }
}

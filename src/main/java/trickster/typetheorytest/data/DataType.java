package trickster.typetheorytest.data;

import java.util.Optional;

public abstract class DataType<TData extends DataType<TData>> {
    private final String typeName;
    private final DataConstructor constructor;
    private final Tuple value;

    protected DataType(String typeName, DataConstructor constructor, Tuple value) {
        this.typeName = typeName;
        this.constructor = constructor;
        this.value = value;
    }

    public boolean matches(DataConstructor constructor) {
        return this.constructor == constructor;
    }

    public <TValue extends Tuple, TCon extends DataConstructor<TData, TCon, TValue>> Optional<TValue> value(
            DataConstructor<TData, TCon, TValue> constructor
    ) {
        return constructor.tryCast(this.constructor, this.value);
    }

    @Override
    public String toString() {
        return typeName + "." + constructor.name() + value.asString();
    }
}

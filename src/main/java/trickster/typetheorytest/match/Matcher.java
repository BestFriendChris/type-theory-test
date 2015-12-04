package trickster.typetheorytest.match;

import trickster.typetheorytest.data.DataType;

import java.util.Optional;

public class Matcher<TData extends DataType<TData>> {
    public static <TData extends DataType<TData>> Matcher<TData> match(TData dataType) {
        return new Matcher<TData>(dataType);
    }

    private final TData dataType;

    private Matcher(TData dataType) {
        this.dataType = dataType;
    }

    @SafeVarargs
    public final <TVal> TVal on(MatchTest<TVal, TData> firstTest, MatchTest<TVal, TData>... otherTests) {
        Optional<TVal> maybeValue = firstTest.matches(dataType);
        if (maybeValue.isPresent()) {
            return maybeValue.get();
        }

        for (MatchTest<TVal, TData> otherTest : otherTests) {
            maybeValue = otherTest.matches(dataType);

            if (maybeValue.isPresent()) {
                return maybeValue.get();
            }
        }

        throw new RuntimeException("No matches");
    }
}

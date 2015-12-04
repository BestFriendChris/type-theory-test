package trickster.typetheorytest.data;

import java.util.Objects;

public abstract class Tuple {
    public static final t0 UNIT = new t0();

    private Tuple() {
    }

    public abstract String asString();

    @Override
    public String toString() {
        return "Tuple" + asString();
    }

    public static class t0 extends Tuple {
        private t0() {
        }

        @Override
        public String asString() {
            return "()";
        }
    }

    public static class t1<A> extends Tuple {
        public final A a;

        public t1(A a) {
            this.a = a;
        }

        @Override
        public String asString() {
            return "(" + a + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            t1<?> t1 = (t1<?>) o;
            return Objects.equals(a, t1.a);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a);
        }
    }

    public static class t2<A, B> extends Tuple {
        public final A a;
        public final B b;

        public t2(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String asString() {
            return "(" + a + ", " + b + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            t2<?, ?> t2 = (t2<?, ?>) o;
            return Objects.equals(a, t2.a) &&
                    Objects.equals(b, t2.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static class t3<A, B, C> extends Tuple {
        public final A a;
        public final B b;
        public final C c;

        public t3(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String asString() {
            return "(" + a + ", " + b + ", " + c + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            t3<?, ?, ?> t3 = (t3<?, ?, ?>) o;
            return Objects.equals(a, t3.a) &&
                    Objects.equals(b, t3.b) &&
                    Objects.equals(c, t3.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }

    public static class t4<A, B, C, D> extends Tuple {
        public final A a;
        public final B b;
        public final C c;
        public final D d;

        public t4(A a, B b, C c, D d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public String asString() {
            return "(" + a + ", " + b + ", " + c + ", " + d + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            t4<?, ?, ?, ?> t4 = (t4<?, ?, ?, ?>) o;
            return Objects.equals(a, t4.a) &&
                    Objects.equals(b, t4.b) &&
                    Objects.equals(c, t4.c) &&
                    Objects.equals(d, t4.d);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c, d);
        }
    }

    public static class t5<A, B, C, D, E> extends Tuple {
        public final A a;
        public final B b;
        public final C c;
        public final D d;
        public final E e;

        public t5(A a, B b, C c, D d, E e) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }

        @Override
        public String asString() {
            return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            t5<?, ?, ?, ?, ?> t5 = (t5<?, ?, ?, ?, ?>) o;
            return Objects.equals(a, t5.a) &&
                    Objects.equals(b, t5.b) &&
                    Objects.equals(c, t5.c) &&
                    Objects.equals(d, t5.d) &&
                    Objects.equals(e, t5.e);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c, d, e);
        }
    }
}

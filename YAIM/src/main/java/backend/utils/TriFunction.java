package backend.utils;

public interface  TriFunction <A,R> {
    R compute(A first, A second, A third);
}

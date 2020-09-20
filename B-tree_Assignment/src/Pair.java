public class Pair<T> {

    private Integer first;
    private T second;

    Pair(){
        this.first = 0;
        this.second = null;
    }

    Pair(Integer first, T second){
        this.first = first;
        this.second = second;
    }

    public Integer first(){
        return this.first;
    }

    public void setFirst(Integer first) { this.first = first; }

    public T second(){
        return this.second;
    }

    public void setSecond(T second) { this.second = second; }
}

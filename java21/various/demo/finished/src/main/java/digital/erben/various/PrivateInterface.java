package digital.erben.various;

public interface PrivateInterface {
    private static String staticPrivate() {
        return "static private";
    }

    private String instancePrivate() {
        return "instance private";
    }

    public default void check() {
        System.out.println(staticPrivate());
        System.out.println(this.instancePrivate());
    }
}
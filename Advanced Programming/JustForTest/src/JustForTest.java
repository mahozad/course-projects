public class JustForTest {
    public static void main(String args[]) {
        Conditions con=Conditions.Off;
    }
}
enum Conditions{
    On,Off,Idle("asleep");
    Conditions(String a){

    }
}
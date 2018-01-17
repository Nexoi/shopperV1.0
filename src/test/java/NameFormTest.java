/**
 * Created by neo on 28/09/2017.
 */
public class NameFormTest {
    public static void main(String[] args) {
        NameFormTest nameFormTest = new NameFormTest();
        System.out.println(nameFormTest.formName("357te@name.com"));
        System.out.println(nameFormTest.formName("s@name.com"));
        System.out.println(nameFormTest.formName("33me.com"));
        System.out.println(nameFormTest.formName("3com"));
        System.out.println(nameFormTest.formName("357te@n.com"));
        System.out.println(nameFormTest.formName("357te@name.sd"));
        System.out.println(nameFormTest.formName("357aame.com"));
        System.out.println(nameFormTest.formName(".com"));
        System.out.println(nameFormTest.formName(""));
        System.out.println(nameFormTest.formName("357aame.com"));
    }

    private String formName(String name) {
        if (name == null) return "";
        if (name.length() > 4) {
            int subLength = name.length() / 2;
            subLength = subLength > 5 ? 5 : subLength;
            return name.substring(0, subLength) + "****" + name.substring(name.length() - subLength, name.length());
        }
        return name;
    }
}

package liu.chun.dong.common.util;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:46
 * @comment TestDto
 */
public class TestDto {
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    TestDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

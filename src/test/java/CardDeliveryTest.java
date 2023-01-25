import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidData() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима Ва-сютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldValidDataWithDateMoreThreeDays() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldDateTwentyNineLeapYear() {
        LocalDate today = LocalDate.now();
        for (int i = 1; i < 4; i++) {
            if (today.isLeapYear() == true) {
                break;

            }
            today = today.plusYears(i);
        }
        String date = "29." + "02." + today.getYear();

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldValidDataYoNameCity() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("Орёл");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldCityNotAdminCenter() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("Вязьма");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), visible);

    }

    @Test
    void shouldCityNameLatin() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("Moscow");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), visible);

    }

    @Test
    void shouldCityNameSymbols() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("!!!!!");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), visible);

    }

    @Test
    void shouldCityNameNumbers() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("12458");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), visible);

    }

    @Test
    void shouldCityNameEmpty() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldCityNameSpaces() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("    ");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldCityNameTab() {
        String date = generateDate(7);

        $("[data-test-id=city] input").setValue("       ");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldDateLessThanThreeDays() {
        String date = generateDate(1);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"), visible);

    }

    @Test
    void shouldDateInThePast() {
        String date = generateDate(-2);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"), visible);

    }

    @Test
    void shouldDateNoExist() {
        LocalDate today = LocalDate.now();
        String date = "35" + "12" + today.getYear();

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateTwentyNineNoLeapYear() {
        LocalDate today = LocalDate.now();
        if (today.isLeapYear()) {
            today = today.plusYears(1);
        }
        String date = "29" + "02" + today.getYear();

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateSymbol() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("@@@@@@@@");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateLetters() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("fffфффф");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateEmpty() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateSpaces() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("     ");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldDateTab() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("       ");
        $("[name=name]").setValue("Дима Васютин");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] span .input__sub").shouldHave(Condition.text("Неверно введена дата"), visible);

    }

    @Test
    void shouldNameLatin() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Dima");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), visible);

    }

    @Test
    void shouldNameSymbol() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("%^^^^");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), visible);

    }

    @Test
    void shouldNameNumbers() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("855244");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), visible);

    }

    @Test
    void shouldNameSpaces() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("     ");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldNameTab() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("         ");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldNameEmpty() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("");
        $("[name=phone]").setValue("+79999554555");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldPhoneLetters() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("аааdssdsd");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), visible);

    }

    @Test
    void shouldPhoneSymbol() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("!!!!!!!!&&&");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), visible);

    }

    @Test
    void shouldPhoneMoreElevenNumber() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+799999999999999999999999999");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), visible);

    }

    @Test
    void shouldPhoneLessElevenNumber() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+79999");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), visible);

    }

    @Test
    void shouldPhoneSpaces() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("    ");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldPhoneTab() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("            ");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldPhoneEmpty() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("");
        $("[data-test-id=agreement] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] span .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), visible);

    }

    @Test
    void shouldEmptyCheckBox() {
        String date = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        assertEquals($("[data-test-id=date] input").getValue(), date);
        $("[name=name]").setValue("Дима ВА-сютин");
        $("[name=phone]").setValue("+79999995555");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("label.input_invalid").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"), visible);

    }


}

package ru.netology;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class Registration {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);
    SelenideElement notification = $x("//div[@data-test-id='notification']");

    @Test
    public void RegistrationForCardDelivery() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Воронеж");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[name=name]").setValue("Мирных Станислав");
        $("[name=phone]").setValue("+79256331548");
        $("[data-test-id=agreement]").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
}

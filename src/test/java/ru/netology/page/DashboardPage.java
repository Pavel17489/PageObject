package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import javax.swing.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        var text = getCard(cardInfo).getText();
        return extractBalance(text);
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo){
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    private int extractBalance(String text){
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    public void reloadDashboardPage(){
        reloadButton.click();
        heading.shouldBe(visible);
    }

    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance){
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()))
                .should(visible)
                .should(text(balanceStart + expectedBalance + balanceFinish));
    }


}

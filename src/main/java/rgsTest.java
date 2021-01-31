import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class rgsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "webDriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10, 2000);
        String baseUrl = "https://www.rgs.ru";
        driver.get(baseUrl);
    }

    @Test
    public void rgsTestScript() {

        String menuXPath = "//a[contains(text(),'Меню') and contains(@class, 'hidden-xs')]";
        WebElement menuButton = driver.findElement(By.xpath(menuXPath));
        waitUtilElementToBeClickable(menuButton);
        menuButton.click();

        String forCompaniesXPath = "//a[contains(text(),'Компаниям')]";
        WebElement forCompaniesButton = driver.findElement(By.xpath(forCompaniesXPath));
        waitUtilElementToBeClickable(forCompaniesButton);
        forCompaniesButton.click();

        String healthXPath = "//a[contains(text(),'Здоровье') and contains(@class, 'list-group-item adv-analytics-navigation-line4-link')]";
        WebElement healthButton = driver.findElement(By.xpath(healthXPath));
        waitUtilElementToBeClickable(healthButton);
        healthButton.click();

        String dmsXPath = "//a[contains(text(),'Добровольное медицинское страхование')]";
        WebElement dmsButton = driver.findElement(By.xpath(dmsXPath));
        waitUtilElementToBeClickable(dmsButton);
        dmsButton.click();

        String getFormXPath = "//a[contains(text(),'Отправить заявку')]";
        WebElement getFormButton = driver.findElement(By.xpath(getFormXPath));
        waitUtilElementToBeClickable(getFormButton);
        getFormButton.click();

        String fieldXPath = "//input[@name='%s']";
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "LastName"))), "Иванов");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "FirstName"))), "Иван");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "MiddleName"))), "Иванович");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "Email"))), "invailedEmail");

        WebElement phoneInput = driver.findElement(By.xpath("//input[contains(@data-bind,'value: Phone')]"));
        waitUtilElementToBeClickable(phoneInput);
        phoneInput.click();
        phoneInput.sendKeys("1112223344");
        Assert.assertEquals("Поле было заполнено некорректно", "+7 (111) 222-33-44", phoneInput.getAttribute("value"));
        Select chooseRegion = new Select(driver.findElement(By.xpath("//select[contains(@data-bind,'options')]")));
        chooseRegion.selectByVisibleText("Москва");

        WebElement dateInput = driver.findElement(By.xpath("//input[@class='dateInput form-control']"));
        waitUtilElementToBeClickable(dateInput);
        dateInput.click();
        dateInput.sendKeys("14.02.2021\n");
        Assert.assertEquals("Поле было заполнено некорректно", "14.02.2021", dateInput.getAttribute("value"));

        WebElement checkboxButton = driver.findElement(By.xpath("//input[@class='checkbox']"));
        checkboxButton.click();
        Assert.assertTrue("Поле было заполнено некорректно", checkboxButton.isSelected());

        WebElement sendButton = driver.findElement(By.xpath("//button[@id='button-m']"));
        waitUtilElementToBeClickable(sendButton);
        sendButton.click();

        WebElement error = driver.findElement(By.xpath("//span[@class='validation-error-text' and ../../../label[contains(text(),'Эл. почта')]]"));
        Assert.assertEquals("Проверка ошибки у поля не была пройдена", "Введите адрес электронной почты", error.getText());

    }


    @After
    public void after(){
        driver.quit();
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void fillInputField(WebElement element, String value) {
        waitUtilElementToBeClickable(element);
        element.click();
        element.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно",
                value, element.getAttribute("value"));
    }
}
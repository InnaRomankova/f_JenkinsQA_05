package model.page;

import io.qameta.allure.Step;
import model.page.base.BaseStatusPage;
import model.component.menu.HomeSideMenuComponent;
import model.page.base.MainBasePageWithSideMenu;
import model.page.config.FolderConfigPage;
import model.page.config.FreestyleProjectConfigPage;
import model.page.config.PipelineConfigPage;
import model.page.status.*;
import model.page.view.NewViewFromDashboardPage;
import model.page.view.ViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.scrollToElement;

public class HomePage extends MainBasePageWithSideMenu<HomeSideMenuComponent> {

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(linkText = "Configure")
    private WebElement configureDropDownMenu;

    @FindBy(linkText = "Rename")
    private WebElement renameDropDownMenu;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButtonInDropDownMenu;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = ".tabBar>.tab>a.addTab")
    private WebElement addViewLink;

    @FindBy(xpath = "//span[text()='Move']")
    private WebElement moveButtonDropdown;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> viewList;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(xpath = "(//a[@class='yuimenuitemlabel'])[3]/span")
    private WebElement buildNowButton;

    @FindBy(css = "#projectstatus th")
    private List<WebElement> listJobTableHeaders;

    @FindBy(xpath = "//div[@class='tabBar']//a[text()='All']/..")
    private WebElement viewAllTab;

    @FindBy(css = "#tasks a")
    private List<WebElement> sideMenuList;

    @FindBy(id = "systemmessage")
    private WebElement systemMessage;

    @FindBy(xpath = "//a[@href='computer/new']")
    private WebElement setUpAnAgent;

    @Override
    protected HomeSideMenuComponent createSideMenuComponent() {
        return new HomeSideMenuComponent(getDriver());
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on new view icon")
    public NewViewFromDashboardPage<?> clickAddViewLink() {
        addViewLink.click();

        return new NewViewFromDashboardPage<>(getDriver(), null);
    }

    @Step("Get list of projects' names")
    public List<String> getJobNamesList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getNumberOfJobsContainingString(String string) {

        return (int) jobList
                .stream()
                .filter(element -> element.getText().contains(string))
                .count();
    }

    public List<String> getViewList() {
        return viewList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Click 'Freestyle project' on the Home page")
    public FreestyleProjectStatusPage clickFreestyleProjectName() {
        getWait(10).until(ExpectedConditions.visibilityOfAllElements(jobList)).get(0).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    @Step("Select ‘Freestyle project’ '{name}' on the Dashboard")
    public FreestyleProjectStatusPage clickFreestyleProjectName(String name) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(By.linkText(name))).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public RenameItemPage<PipelineStatusPage> clickRenamePipelineDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new PipelineStatusPage(getDriver()));
    }

    public RenameItemPage<MultiConfigurationProjectStatusPage> clickRenameMultiConfigurationDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new MultiConfigurationProjectStatusPage(getDriver()));
    }

    public RenameItemPage<FolderStatusPage> clickRenameFolderDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new FolderStatusPage(getDriver()));
    }

    @Step("Click 'Rename' in the drop-down menu")
    public RenameItemPage<MultibranchPipelineStatusPage> clickRenameMultibranchPipelineDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new MultibranchPipelineStatusPage(getDriver()));
    }

    public ConfigurationGeneralPage clickConfigDropDownMenu() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new ConfigurationGeneralPage(getDriver());
    }

    public PipelineStatusPage clickPipelineProjectName() {
        jobList.get(0).click();

        return new PipelineStatusPage(getDriver());
    }

    @Step("Click Delete in the drop-down menu")
    public DeletePage<HomePage> clickDeleteDropDownMenu() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(deleteButtonInDropDownMenu));
        deleteButtonInDropDownMenu.click();

        return new DeletePage<>(getDriver(), this);
    }

    @Step("Click the job drop-down menu with name '{name}' on the dashboard")
    public HomePage clickJobDropDownMenu(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", name)))).click();

        return this;
    }
    @Step("Click on MultibranchPipeline name '{name}' on the dashboard")
    public MultibranchPipelineStatusPage clickJobMultibranchPipeline(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }

    @Step("Click configure in the drop-down menu")
    public PipelineConfigPage clickConfigureDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new PipelineConfigPage(getDriver());
    }

    @Step("Get Header Text")
    public String getHeaderText() {

        return getWait(10).until(ExpectedConditions.visibilityOf(header)).getText();
    }

    @Step("Click on Folder name in dashboard")
    public FolderStatusPage clickFolder(String folderName) {
        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']")).click();

        return new FolderStatusPage(getDriver());
    }

    public FolderConfigPage clickConfigureDropDownMenuForFolder() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FolderConfigPage(getDriver());
    }

    @Step("Get job build status on the Home page")
    public String getJobBuildStatus(String name) {
        return getDriver().findElement(By.id(String.format("job_%s", name)))
                .findElement(By.xpath(".//*[name()='svg']")).getAttribute("tooltip");
    }

    public MultiConfigurationProjectStatusPage clickMultiConfigurationProject(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.linkText(name))).click();
        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public <T extends BaseStatusPage<T, ?>> MovePage<T> clickMoveButtonDropdown(T baseStatusPage) {
        getWait(5).until(ExpectedConditions.visibilityOf(moveButtonDropdown));
        scrollToElement(getDriver(), moveButtonDropdown);
        moveButtonDropdown.click();
        return new MovePage<>(getDriver(), baseStatusPage);
    }

    public String getBuildDurationTime() {
        if (getJobBuildStatus().equals("Success")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[4]")).getText();
        } else if (getJobBuildStatus().equals("Failed")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[5]")).getText();
        }

        return null;
    }

    public String getJobBuildStatus() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public ViewPage clickView(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/view/%s/']", name))).click();

        return new ViewPage(getDriver());
    }

    public String getLastSuccessText(String name) {

        return getDriver().findElement(By.xpath(String.format("//*[@id='job_%s']/td[4]", name))).getText();
    }

    public String getJobName(String name) {

        return getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
    }

    public String getStatusBuildText() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public HomePage movePointToCheckBox() {
        getAction().moveToElement(buildStatusIcon).perform();

        return this;
    }

    @Step("Select 'Configure' in the dropdown menu")
    public FreestyleProjectConfigPage clickConfigDropDownMenuFreestyle() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public HomePage clickProjectDropdownMenu(String projectName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + projectName + "/']/button"))).click();

        return this;
    }

    public boolean buildNowButtonIsDisplayed() {

        return getWait(5).until(ExpectedConditions.visibilityOf(buildNowButton)).isDisplayed();
    }

    public OrgFolderStatusPage clickOrgFolder(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new OrgFolderStatusPage(getDriver());
    }

    @Step("Click pipeline '{name}' on dashboard")
    public PipelineStatusPage clickPipelineJob(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new PipelineStatusPage(getDriver());
    }

    public String getJobListAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : jobList) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }

    public int getJobTableHeadersSize() {
        return listJobTableHeaders.size();
    }

    @Step("Get attribute view 'All'")
    public String getAttributeViewAll() {
        return viewAllTab.getAttribute("class");
    }

    @Step("Get list of side menu options")
    public List<String> getSideMenuList() {
        return sideMenuList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Click attribute view 'All'")
    public HomePage clickViewAll() {
        viewAllTab.click();

        return this;
    }

    @Step("Get 'System Message' from dashboard")
    public String getSystemMessageFromDashboard() {
       return systemMessage.getText();
    }

    @Step("Click on 'Set up an agent' on dashboard")
    public NewNodePage clickOnSetUpAnAgent() {
        setUpAnAgent.click();
        return new NewNodePage(getDriver());
    }
}
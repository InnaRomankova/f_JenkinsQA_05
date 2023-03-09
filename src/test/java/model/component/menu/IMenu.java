package model.component.menu;

import model.IModel;
import model.page.RenameItemPage;
import model.page.base.BaseStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public interface IMenu<StatusPage extends BaseStatusPage<?, ?>> extends IModel {

    StatusPage getStatusPage();

    default RenameItemPage<StatusPage> clickRename() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Rename"))).click();

        return new RenameItemPage<>(getDriver(), getStatusPage());
    }
}

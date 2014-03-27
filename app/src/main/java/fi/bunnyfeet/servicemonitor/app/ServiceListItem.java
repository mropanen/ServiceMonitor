package fi.bunnyfeet.servicemonitor.app;

/**
 * Created by Mikko on 26.3.2014.
 */
public class ServiceListItem {

    private String itemTitle;
    private String itemUrl;

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String title) {
        itemTitle = title;
    }

    public String getItemUrl() {
        return itemUrl;
    }
    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public ServiceListItem(String title, String url) {
        itemTitle = title;
        itemUrl = url;
    }

    @Override
    public String toString() {
        return itemTitle;
    }
}

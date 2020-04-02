package my.upgrade007.upgrade;

class LinksInDB {

    String LinkName,LInk;

    public LinksInDB(String linkName, String LInk) {
        this.LinkName = linkName;
        this.LInk = LInk;
    }

    public String getLinkName() {
        return LinkName;
    }

    public void setLinkName(String linkName) {
        LinkName = linkName;
    }

    public String getLInk() {
        return LInk;
    }

    public void setLInk(String LInk) {
        this.LInk = LInk;
    }
}

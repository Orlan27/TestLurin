package com.zed.lurin.domain.jpa.view;

import javax.persistence.*;

/**
 * <p>Entity that represents a menus for user name</p>
 * @author Francisco Lujano
 */
@Entity
@Table(name = "v_menu_from_user")
public class MenuByUserNameView {

    /**
     * View Menu by UserNameId
     */
    @Id
    @Column(name = "id", nullable = false)
    private long code;

    /**
     * Login the View Menu by UserName
     */
    @Column(name="login", nullable = false)
    private String login;

    /**
     * Menu ID the View Menu by UserName
     */
    @Column(name="menu_id", nullable = false)
    private long menuid;

    /**
     * Name the View Menu by UserName
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Title the View Menu by UserName
     */
    @Column(name="title", nullable = false)
    private String title;

    /**
     * default I18n the View Menu by UserName
     */
    @Column(name="DEFAULT_I18N", nullable = false)
    private String defaultI18n;

    /**
     * Lang ES the View Menu by UserName
     */
    @Column(name="es", nullable = false)
    private String langES;

    /**
     * Lang EN the View Menu by UserName
     */
    @Column(name="en", nullable = false)
    private String langEN;

    /**
     * Lang PR the View Menu by UserName
     */
    @Column(name="pr", nullable = false)
    private String langPR;

    /**
     * Url the View Menu by UserName
     */
    @Column(name="url", nullable = false)
    private String url;


    /**
     * parent menu the View Menu by UserName
     */
    @Column(name="menu_parent_id", nullable = false)
    private long menuParent;

    /**
     * Order menu the View Menu by UserName
     */
    @Column(name="ordering", nullable = false)
    private long ordering;

    /**
     * Icon name the View Menu by UserName
     */
    @Column(name="icon_name", nullable = false)
    private String iconName;

    /**
     * Table ID the View Menu by UserName
     */
    @Column(name="table_id", nullable = false)
    private long tableId;

    /**
     * Url for replace the View Menu by UserName
     */
    @Column(name="URL_REPLACE", nullable = false)
    private String urlReplace;


    @Column(name="IS_CREATE", nullable=false, precision=1, scale=0)
    private boolean isCreate;

    @Column(name="IS_EDIT", nullable=false, precision=1, scale=0)
    private boolean isEdit;

    @Column(name="IS_DELETE", nullable=false, precision=1, scale=0)
    private boolean isDelete;

    @Column(name="IS_GET", nullable=false, precision=1, scale=0)
    private boolean isGet;

    @Column(name="IS_ALLOW_ALL", nullable=false, precision=1, scale=0)
    private boolean isAllowAll;

    @Transient
    private String langAssigned;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getMenuid() {
        return menuid;
    }

    public void setMenuid(long menuid) {
        this.menuid = menuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefaultI18n() {
        return defaultI18n;
    }

    public void setDefaultI18n(String defaultI18n) {
        this.defaultI18n = defaultI18n;
    }

    public String getLangES() {
        return langES;
    }

    public void setLangES(String langES) {
        this.langES = langES;
    }

    public String getLangEN() {
        return langEN;
    }

    public void setLangEN(String langEN) {
        this.langEN = langEN;
    }

    public String getLangPR() {
        return langPR;
    }

    public void setLangPR(String langPR) {
        this.langPR = langPR;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getOrdering() {
        return ordering;
    }

    public void setOrdering(long ordering) {
        this.ordering = ordering;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getLangAssigned() {
        return langAssigned;
    }

    public void setLangAssigned(String langAssigned) {
        this.langAssigned = langAssigned;
    }

    public long getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(long menuParent) {
        this.menuParent = menuParent;
    }

    public String getUrlReplace() {
        return urlReplace;
    }

    public void setUrlReplace(String urlReplace) {
        this.urlReplace = urlReplace;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean get) {
        isGet = get;
    }

    public boolean isAllowAll() {
        return isAllowAll;
    }

    public void setAllowAll(boolean allowAll) {
        isAllowAll = allowAll;
    }
}

package com.zed.master.deploy.filter;

import com.google.gson.Gson;
import com.zed.lurin.domain.jpa.view.MenuByUserNameView;
import com.zed.lurin.security.keys.Keys;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * filter that intercepts the login and creates the redirection to the corresponding page
 * @author Francisco Lujano
 */
public class PostLoginFilter implements Filter {

    /*
     * Logger
     */
    private static Logger LOGGER = Logger.getLogger(PostLoginFilter.class.getName());


    protected FilterConfig filterConfig;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        /**
         * Document: https://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.nd.multiplatform.doc/ae/tsec_servlet.html
         */
        Gson gson = new Gson();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String menuToJson = (String)request.getSession().getAttribute(Keys.MENU.toString());

        MenuByUserNameView[] menuByUserNameViews = gson.fromJson(menuToJson, MenuByUserNameView[].class);

        List<MenuByUserNameView> menuByUserNameViewList = Arrays.asList(menuByUserNameViews);

        menuByUserNameViewList = menuByUserNameViewList.stream().filter(me->me.getMenuParent()>0).collect(Collectors.toList());

        menuByUserNameViewList = menuByUserNameViewList.stream().sorted(Comparator.comparing(MenuByUserNameView::getOrdering)).collect(Collectors.toList());

        String url = "/logout";

        Optional<MenuByUserNameView> optional = menuByUserNameViewList.stream().findFirst();
        if(optional.isPresent()){
            MenuByUserNameView menuByUserNameView =  optional.get();
            if(menuByUserNameView.getUrlReplace()!=null){
                url = menuByUserNameView.getUrlReplace();
            }else{
                url = menuByUserNameView.getUrl();
            }
        }

        ((HttpServletResponse) servletResponse).sendRedirect(url);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}

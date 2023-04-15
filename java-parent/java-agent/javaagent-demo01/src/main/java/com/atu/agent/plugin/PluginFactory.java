package com.atu.agent.plugin;

import com.atu.agent.plugin.jvm.JvmPlugin;
import com.atu.agent.plugin.link.LinkPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Tom
 * @date: 2023/4/15 13:55
 * @description: TODO
 **/
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new LinkPlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }

}
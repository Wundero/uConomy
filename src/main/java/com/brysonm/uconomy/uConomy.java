package com.brysonm.uconomy;

import com.brysonm.uconomy.commands.*;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.java.JavaPlugin;


public class uConomy extends JavaPlugin {

    private static uConomy instance;

    private static YMLFactory.YML balancesYML;

    private static YMLFactory.YML salesYML;
    
    private static boolean vaultExists;

    public void onEnable() {
        
        if(getServer().getPluginManager().isPluginEnabled("Vault")) {
            vaultExists = true;
        } else {
            vaultExists = false;
        }
        
        saveDefaultConfig();
        instance = this;
        balancesYML = YMLFactory.buildYML("balances", this);
        salesYML = YMLFactory.buildYML("sales", this);
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("deposit").setExecutor(new DepositCommand());
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("sell").setExecutor(new SellCommand());
        getCommand("buy").setExecutor(new BuyCommand());
        getCommand("price").setExecutor(new PriceCommand());

        if(!getConfig().getBoolean("migrated")) {

            SaleUtils.migrateUUIDs();

            getConfig().set("migrated", true);

            saveConfig();

        }

        SaleUtils.loadSales();
        
        registerEconomy();
    }
    
    private boolean registerEconomy() {
        
        if(vaultExists) {
            final ServicesManager sm = getServer().getServicesManager();
            //TODO vault connection class and register. Implement the Economy class and override the methods needed to make proper economy.
            //Make sure to implement other things in class and use existing eco
        } else {
            //Have boolean with usingVault - could be useful later depending on features needed.
        }
        
    }

    public void onDisable() {
        balancesYML.saveConfig();
        salesYML.saveConfig();
    }

    public static uConomy getInstance() {
        return instance;
    }

    public static YMLFactory.YML getBalancesYML() {
        return balancesYML;
    }

    public static YMLFactory.YML getSalesYML() {
        return salesYML;
    }
}

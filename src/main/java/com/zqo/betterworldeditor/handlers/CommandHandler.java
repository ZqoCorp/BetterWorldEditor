package com.zqo.betterworldeditor.handlers;

import com.google.common.reflect.ClassPath;
import com.zqo.betterworldeditor.BetterWorldEditor;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class CommandHandler
{
    private final BetterWorldEditor eco = BetterWorldEditor.getBetterWorldEditor();

    public void register()
    {
        try {
            final ClassPath classPath = ClassPath.from(eco.getClass().getClassLoader());

            classPath.getTopLevelClassesRecursive("com.zqo.betterworldeditor.commands").forEach((classInfo -> {
                try {
                    final Class<?> c = Class.forName(classInfo.getName());
                    final Object obj = c.getDeclaredConstructor().newInstance();

                    if (obj instanceof Command command) {
                        final SimpleCommandMap commandMap = ((CraftServer) eco.getServer()).getCommandMap();
                        commandMap.register(eco.getDescription().getName(), command);
                    }

                } catch (IllegalAccessException | ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package BananaFructa.reim;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Utils {

    public static class InstanceField<T> {
        Field f;
        Object parent;

        public InstanceField(Field exposedField, Object parent) {
            this.f = exposedField;
            this.parent = parent;
        }

        public T get() {
            try {
                return (T)f.get(parent);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public T set(T value) {
            try {
                f.set(parent,value);
                return value;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> T readDeclaredField(Class<?> targetType, Object target, String name) {
        try {
            Field f = targetType.getDeclaredField(name);
            f.setAccessible(true);
            return (T) f.get(target);
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    public static void writeDeclaredField(Class<?> targetType, Object target, String name, Object value,boolean final_) {
        try {
            Field f = targetType.getDeclaredField(name);
            f.setAccessible(true);
            if (final_) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            }
            f.set(target,value);

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static <T> InstanceField<T> getAccessibleField(Class<?> targetType, Object target, String name, boolean final_) {
        try {
            Field f = targetType.getDeclaredField(name);
            f.setAccessible(true);
            if (final_) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            }

            return new InstanceField<T>(f,target);

        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    public static void writeDeclaredDouble(Class<?> targetType, Object target, String name, double value,boolean final_) {
        try {
            Field f = targetType.getDeclaredField(name);
            f.setAccessible(true);
            if (final_) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            }
            f.setDouble(target,value);

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static Method getDeclaredMethod(Class<?> targetClass, String name, Class<?>... parameters) {
        try {
            Method m = targetClass.getDeclaredMethod(name, parameters);
            m.setAccessible(true);
            return m;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static ItemStack itemStackFromCTId(String id) {
        id = id.replace("<","").replace(">","");
        String[] s = id.split(":");
        Item item;
        int type = 0;
        if (s[s.length - 1].matches("[0-9]+")) {
            item = Item.REGISTRY.getObject(new ResourceLocation(String.join(":", Arrays.copyOfRange(s,0,s.length-1))));
            type = Integer.parseInt(s[s.length - 1]);
        } else {
            item = Item.REGISTRY.getObject(new ResourceLocation(id));
        }
        return new ItemStack(item,1,type);
    }

    public static ItemStack itemStackFromCTId(String id,int amount) {
        id = id.replace("<","").replace(">","");
        String[] s = id.split(":");
        Item item;
        int type = 0;
        if (s[s.length - 1].matches("[0-9]+")) {
            item = Item.REGISTRY.getObject(new ResourceLocation(String.join(":", Arrays.copyOfRange(s,0,s.length-1))));
            type = Integer.parseInt(s[s.length - 1]);
        } else {
            item = Item.REGISTRY.getObject(new ResourceLocation(id));
        }
        return new ItemStack(item,amount,type);
    }

    public static Fluid fluidFromCTId(String name) {
        return FluidRegistry.getFluid(name.replace("<","").replace(">","").replace("liquid:",""));
    }

    public static boolean ItemStacksEqual(ItemStack s1,ItemStack s2) {
        if (s1.getCount() != s2.getCount()) return false;
        if (s1.getMetadata() != s2.getMetadata()) return false;
        if (s1.getItem() != s2.getItem()) return false;
        return true;
    }
}

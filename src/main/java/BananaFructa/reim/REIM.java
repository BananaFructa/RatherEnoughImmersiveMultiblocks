package BananaFructa.reim;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = REIM.modId,version = REIM.version,name = REIM.name,dependencies = "after:jei;after:immersiveengineering")
public class REIM {

    public static final String modId = "reim";
    public static final String name = "Rather Enough Immersive Multiblocks";
    public static final String version = "0.2";

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new GetPosCommand());
    }


}

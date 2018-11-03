package ch.dentax.saoaddon.main;

import static ch.dentax.saoaddon.main.References.CLIENT_PROXY;
import static ch.dentax.saoaddon.main.References.MOD_ID;
import static ch.dentax.saoaddon.main.References.MOD_NAME;
import static ch.dentax.saoaddon.main.References.MOD_VERSION;
import static ch.dentax.saoaddon.main.References.SERVER_PROXY;

import ch.dentax.saoaddon.block.BlockMod;
import ch.dentax.saoaddon.gui.GuiHandlerWoodenOven;
import ch.dentax.saoaddon.gui.GuiSkills;
import ch.dentax.saoaddon.item.ItemMod;
import ch.dentax.saoaddon.proxys.CommonProxy;
import ch.dentax.saoaddon.skills.PacketSkills;
import ch.dentax.saoaddon.skills.TutEventHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION)
public class SAOMod {

    public static SimpleNetworkWrapper network;


    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance(MOD_ID)
    public static SAOMod instance;

    public static CreativeTabs tabSAO = new CreativeTabs("tabSAO") {


        @Override
        public Item getTabIconItem() {
            // TODO Auto-generated method stub
            return ItemMod.emptybarrel;
        }


    };
    public static CreativeTabs tabSAOFood = new CreativeTabs("tabSAOFood") {


        @Override
        public Item getTabIconItem() {
            // TODO Auto-generated method stub
            return ItemMod.eggplant;
        }


    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {


        network = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_ID);
        network.registerMessage(PacketSkills.Handler.class,PacketSkills.class,0,Side.SERVER);



        BlockMod.init();
        BlockMod.register();
        ItemMod.init();
        ItemMod.register();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {


        MinecraftForge.EVENT_BUS.register(new TutEventHandler());
        FMLCommonHandler.instance().bus().register(new TutEventHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerWoodenOven());



    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            MinecraftForge.EVENT_BUS.register(new GuiSkills(Minecraft.getMinecraft()));

    }


}

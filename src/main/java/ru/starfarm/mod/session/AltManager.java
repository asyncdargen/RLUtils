package ru.starfarm.mod.session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import ru.starfarm.mod.SFUtils;
import ru.starfarm.util.ReflectUtil;

import java.util.UUID;

@Getter @Setter
public class AltManager {

    private UserAuthentication auth;
    private Session session;
    private Minecraft mc;

    protected AltManager(Minecraft mc) {
        this.mc = mc;
        UUID uuid = UUID.randomUUID();
        AuthenticationService service = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), uuid.toString());
        auth = service.createUserAuthentication(Agent.MINECRAFT);
        service.createMinecraftSessionService();
    }


    public Session login(String login, String password) {
        Session session = null;
        auth.logOut();
        auth.setUsername(login);
        auth.setPassword(password);
        try {
            auth.logIn();
            val profile = auth.getSelectedProfile();
            session = new Session(profile.getName(), profile.getId().toString(), auth.getAuthenticatedToken(), auth.getUserType().getName());
        } catch (AuthenticationException e) {
            session = new Session(login, login, "0", "legacy");
        }
        SFUtils.INSTANCE.getPlayerProvider().getAltManager().session = session;
        return session;
    }

    public void restoreDefault() {
        session = ReflectUtil.getFieldValue(Minecraft.getMinecraft(),
                ReflectUtil.findField(Minecraft.class, Session.class));
    }

    public Session getSession() {
        return session == null ? session = ReflectUtil.getFieldValue(Minecraft.getMinecraft(),
                ReflectUtil.findField(Minecraft.class, Session.class)) : session;
    }
}

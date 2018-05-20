package jPham.someBot.bot;

import com.neovisionaries.ws.client.WebSocketFactory;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.audio.factory.IAudioSendFactory;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.impl.JDAImpl;
import net.dv8tion.jda.core.exceptions.AccountTypeException;
import net.dv8tion.jda.core.hooks.IEventManager;
import net.dv8tion.jda.core.managers.impl.PresenceImpl;
import net.dv8tion.jda.core.utils.Checks;
import net.dv8tion.jda.core.utils.SessionController;
import net.dv8tion.jda.core.utils.SessionControllerAdapter;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Used to create new {@link net.dv8tion.jda.core.JDA} instances. This is also useful for making sure all of
 * your {@link net.dv8tion.jda.core.hooks.EventListener EventListeners} are registered
 * before {@link net.dv8tion.jda.core.JDA} attempts to log in.
 *
 * <p>A single JDABuilder can be reused multiple times. Each call to
 * {@link net.dv8tion.jda.core.JDABuilder#buildAsync() buildAsync()} or
 * {@link net.dv8tion.jda.core.JDABuilder#buildBlocking() buildBlocking()}
 * creates a new {@link net.dv8tion.jda.core.JDA} instance using the same information.
 * This means that you can have listeners easily registered to multiple {@link net.dv8tion.jda.core.JDA} instances.
 */
public class JDABuilder
{
    protected final List<Object> listeners;

    protected ConcurrentMap<String, String> contextMap = null;
    protected boolean enableContext = true;
    protected SessionController controller = null;
    protected OkHttpClient.Builder httpClientBuilder = null;
    protected WebSocketFactory wsFactory = null;
    protected AccountType accountType;
    protected String token = null;
    protected IEventManager eventManager = null;
    protected IAudioSendFactory audioSendFactory = null;
    protected JDA.ShardInfo shardInfo = null;
    protected Game game = null;
    protected OnlineStatus status = OnlineStatus.ONLINE;
    protected int maxReconnectDelay = 900;
    protected int corePoolSize = 2;
    protected boolean enableVoice = true;
    protected boolean enableShutdownHook = true;
    protected boolean enableBulkDeleteSplitting = true;
    protected boolean autoReconnect = true;
    protected boolean idle = false;
    protected boolean requestTimeoutRetry = true;
    protected boolean enableCompression = true;

    public JDABuilder(AccountType accountType)
    {
        Checks.notNull(accountType, "accountType");

        this.accountType = accountType;
        this.listeners = new LinkedList<>();
    }

    public JDABuilder setContextMap(ConcurrentMap<String, String> map)
    {
        this.contextMap = map;
        if (map != null)
            this.enableContext = true;
        return this;
    }

    public JDABuilder setContextEnabled(boolean enable)
    {
        this.enableContext = enable;
        return this;
    }

    public JDABuilder setCompressionEnabled(boolean enable)
    {
        this.enableCompression = enable;
        return this;
    }

    public JDABuilder setRequestTimeoutRetry(boolean retryOnTimeout)
    {
        this.requestTimeoutRetry = retryOnTimeout;
        return this;
    }

    public JDABuilder setToken(String token)
    {
        this.token = token;
        return this;
    }

    public JDABuilder setHttpClientBuilder(OkHttpClient.Builder builder)
    {
        this.httpClientBuilder = builder;
        return this;
    }

    public JDABuilder setWebsocketFactory(WebSocketFactory factory)
    {
        this.wsFactory = factory;
        return this;
    }

    public JDABuilder setCorePoolSize(int size)
    {
        Checks.positive(size, "Core pool size");
        this.corePoolSize = size;
        return this;
    }

    public JDABuilder setAudioEnabled(boolean enabled)
    {
        this.enableVoice = enabled;
        return this;
    }

    public JDABuilder setBulkDeleteSplittingEnabled(boolean enabled)
    {
        this.enableBulkDeleteSplitting = enabled;
        return this;
    }

    public JDABuilder setEnableShutdownHook(boolean enable)
    {
        this.enableShutdownHook = enable;
        return this;
    }

    public JDABuilder setAutoReconnect(boolean autoReconnect)
    {
        this.autoReconnect = autoReconnect;
        return this;
    }

    public JDABuilder setEventManager(IEventManager manager)
    {
        this.eventManager = manager;
        return this;
    }

    public JDABuilder setAudioSendFactory(IAudioSendFactory factory)
    {
        this.audioSendFactory = factory;
        return this;
    }

    public JDABuilder setIdle(boolean idle)
    {
        this.idle = idle;
        return this;
    }

    public JDABuilder setGame(Game game)
    {
        this.game = game;
        return this;
    }

    public JDABuilder setStatus(OnlineStatus status)
    {
        if (status == null || status == OnlineStatus.UNKNOWN)
            throw new IllegalArgumentException("OnlineStatus cannot be null or unknown!");
        this.status = status;
        return this;
    }

    public JDABuilder addEventListener(Object... listeners)
    {
        Checks.noneNull(listeners, "listeners");

        Collections.addAll(this.listeners, listeners);
        return this;
    }

    public JDABuilder removeEventListener(Object... listeners)
    {
        Checks.noneNull(listeners, "listeners");

        this.listeners.removeAll(Arrays.asList(listeners));
        return this;
    }

    public JDABuilder setMaxReconnectDelay(int maxReconnectDelay)
    {
        Checks.check(maxReconnectDelay >= 32, "Max reconnect delay must be 32 seconds or greater. You provided %d.", maxReconnectDelay);

        this.maxReconnectDelay = maxReconnectDelay;
        return this;
    }

    public JDABuilder useSharding(int shardId, int shardTotal)
    {
        AccountTypeException.check(accountType, AccountType.BOT);
        Checks.notNegative(shardId, "Shard ID");
        Checks.positive(shardTotal, "Shard Total");
        Checks.check(shardId < shardTotal,
                "The shard ID must be lower than the shardTotal! Shard IDs are 0-based.");
        shardInfo = new JDA.ShardInfo(shardId, shardTotal);
        return this;
    }

    public JDABuilder setSessionController(SessionController controller)
    {
        this.controller = controller;
        return this;
    }

    public JDA buildAsync() throws LoginException
    {
        OkHttpClient.Builder httpClientBuilder = this.httpClientBuilder == null ? new OkHttpClient.Builder() : this.httpClientBuilder;
        WebSocketFactory wsFactory = this.wsFactory == null ? new WebSocketFactory() : this.wsFactory;

        if (controller == null && shardInfo != null)
            controller = new SessionControllerAdapter();

        JDAImpl jda = new JDAImpl(accountType, token, controller, httpClientBuilder, wsFactory, autoReconnect, enableVoice, enableShutdownHook,
                enableBulkDeleteSplitting, requestTimeoutRetry, enableContext, corePoolSize, maxReconnectDelay, contextMap);

        if (eventManager != null)
            jda.setEventManager(eventManager);

        if (audioSendFactory != null)
            jda.setAudioSendFactory(audioSendFactory);

        listeners.forEach(jda::addEventListener);
        jda.setStatus(JDA.Status.INITIALIZED);  //This is already set by JDA internally, but this is to make sure the listeners catch it.

        String gateway = jda.getGateway();

        // Set the presence information before connecting to have the correct information ready when sending IDENTIFY
        ((PresenceImpl) jda.getPresence())
                .setCacheGame(game)
                .setCacheIdle(idle)
                .setCacheStatus(status);
        jda.login(gateway, shardInfo, enableCompression);
        return jda;
    }

    public JDA buildBlocking(JDA.Status status) throws LoginException, InterruptedException
    {
        Checks.notNull(status, "Status");
        Checks.check(status.isInit(), "Cannot await the status %s as it is not part of the login cycle!", status);
        JDA jda = buildAsync();
        while (!jda.getStatus().isInit()                      // JDA might disconnect while starting
                || jda.getStatus().ordinal() < status.ordinal()) // Wait until status is bypassed
        {
            if (jda.getStatus() == Status.SHUTDOWN)
                throw new IllegalStateException("JDA was unable to finish starting up!");
            Thread.sleep(50);
        }

        return jda;
    }

    public JDA buildBlocking() throws LoginException, InterruptedException
    {
        return buildBlocking(Status.CONNECTED);
    }
}
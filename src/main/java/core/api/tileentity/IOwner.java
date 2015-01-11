package core.api.tileentity;

import java.util.UUID;

/**
 * Created by Master801 on 11/17/2014.
 * @author Master801
 */
public interface IOwner {

    String getOwnerUserName();

    UUID getOwnerUUID();

    void setOwnerName(String newOwnerName);

    void setOwnerUUID(UUID newUUID);

}

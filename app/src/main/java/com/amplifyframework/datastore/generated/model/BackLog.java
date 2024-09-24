package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.ModelReference;
import com.amplifyframework.core.model.LoadedModelReferenceImpl;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the BackLog type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "BackLogs", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
}, hasLazySupport = true)
public final class BackLog implements Model {
  public static final BackLogPath rootPath = new BackLogPath("root", false, null);
  public static final QueryField ID = field("BackLog", "id");
  public static final QueryField GAMES = field("BackLog", "games");
  public static final QueryField USER = field("BackLog", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Game") List<Game> games;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userId", targetNames = {"userId"}, type = User.class) ModelReference<User> user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public List<Game> getGames() {
      return games;
  }
  
  public ModelReference<User> getUser() {
      return user;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private BackLog(String id, List<Game> games, ModelReference<User> user) {
    this.id = id;
    this.games = games;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      BackLog backLog = (BackLog) obj;
      return ObjectsCompat.equals(getId(), backLog.getId()) &&
              ObjectsCompat.equals(getGames(), backLog.getGames()) &&
              ObjectsCompat.equals(getUser(), backLog.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), backLog.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), backLog.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getGames())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("BackLog {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("games=" + String.valueOf(getGames()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static BackLog justId(String id) {
    return new BackLog(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      games,
      user);
  }
  public interface BuildStep {
    BackLog build();
    BuildStep id(String id);
    BuildStep games(List<Game> games);
    BuildStep user(User user);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private List<Game> games;
    private ModelReference<User> user;
    public Builder() {
      
    }
    
    private Builder(String id, List<Game> games, ModelReference<User> user) {
      this.id = id;
      this.games = games;
      this.user = user;
    }
    
    @Override
     public BackLog build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new BackLog(
          id,
          games,
          user);
    }
    
    @Override
     public BuildStep games(List<Game> games) {
        this.games = games;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = new LoadedModelReferenceImpl<>(user);
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, List<Game> games, ModelReference<User> user) {
      super(id, games, user);
      
    }
    
    @Override
     public CopyOfBuilder games(List<Game> games) {
      return (CopyOfBuilder) super.games(games);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  

  public static class BackLogIdentifier extends ModelIdentifier<BackLog> {
    private static final long serialVersionUID = 1L;
    public BackLogIdentifier(String id) {
      super(id);
    }
  }
  
}

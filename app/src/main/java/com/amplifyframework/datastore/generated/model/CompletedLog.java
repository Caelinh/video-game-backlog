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

/** This is an auto generated class representing the CompletedLog type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CompletedLogs", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
}, hasLazySupport = true)
public final class CompletedLog implements Model {
  public static final CompletedLogPath rootPath = new CompletedLogPath("root", false, null);
  public static final QueryField ID = field("CompletedLog", "id");
  public static final QueryField USER = field("CompletedLog", "userId");
  public static final QueryField GAMES = field("CompletedLog", "games");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userId", targetNames = {"userId"}, type = User.class) ModelReference<User> user;
  private final @ModelField(targetType="Game") List<Game> games;
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
  
  public ModelReference<User> getUser() {
      return user;
  }
  
  public List<Game> getGames() {
      return games;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private CompletedLog(String id, ModelReference<User> user, List<Game> games) {
    this.id = id;
    this.user = user;
    this.games = games;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CompletedLog completedLog = (CompletedLog) obj;
      return ObjectsCompat.equals(getId(), completedLog.getId()) &&
              ObjectsCompat.equals(getUser(), completedLog.getUser()) &&
              ObjectsCompat.equals(getGames(), completedLog.getGames()) &&
              ObjectsCompat.equals(getCreatedAt(), completedLog.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), completedLog.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getGames())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CompletedLog {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("games=" + String.valueOf(getGames()) + ", ")
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
  public static CompletedLog justId(String id) {
    return new CompletedLog(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      games);
  }
  public interface BuildStep {
    CompletedLog build();
    BuildStep id(String id);
    BuildStep user(User user);
    BuildStep games(List<Game> games);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private ModelReference<User> user;
    private List<Game> games;
    public Builder() {
      
    }
    
    private Builder(String id, ModelReference<User> user, List<Game> games) {
      this.id = id;
      this.user = user;
      this.games = games;
    }
    
    @Override
     public CompletedLog build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CompletedLog(
          id,
          user,
          games);
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = new LoadedModelReferenceImpl<>(user);
        return this;
    }
    
    @Override
     public BuildStep games(List<Game> games) {
        this.games = games;
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
    private CopyOfBuilder(String id, ModelReference<User> user, List<Game> games) {
      super(id, user, games);
      
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder games(List<Game> games) {
      return (CopyOfBuilder) super.games(games);
    }
  }
  

  public static class CompletedLogIdentifier extends ModelIdentifier<CompletedLog> {
    private static final long serialVersionUID = 1L;
    public CompletedLogIdentifier(String id) {
      super(id);
    }
  }
  
}

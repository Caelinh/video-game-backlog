package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.ModelReference;
import com.amplifyframework.core.model.LoadedModelReferenceImpl;
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

/** This is an auto generated class representing the Game type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Games", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
}, hasLazySupport = true)
@Index(name = "undefined", fields = {"id"})
public final class Game implements Model {
  public static final GamePath rootPath = new GamePath("root", false, null);
  public static final QueryField ID = field("Game", "id");
  public static final QueryField AGGREGATED_RATING = field("Game", "aggregated_rating");
  public static final QueryField CATEGORY = field("Game", "category");
  public static final QueryField COVER_URL = field("Game", "cover_url");
  public static final QueryField FIRST_RELEASE_DATE = field("Game", "first_release_date");
  public static final QueryField GENRES = field("Game", "genres");
  public static final QueryField NAME = field("Game", "name");
  public static final QueryField SUMMARY = field("Game", "summary");
  public static final QueryField SIMILAR_GAMES = field("Game", "similar_games");
  public static final QueryField COMPLETE = field("Game", "complete");
  public static final QueryField USER = field("Game", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Float") Double aggregated_rating;
  private final @ModelField(targetType="Int") Integer category;
  private final @ModelField(targetType="String") String cover_url;
  private final @ModelField(targetType="AWSDate") Temporal.Date first_release_date;
  private final @ModelField(targetType="Int") List<Integer> genres;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String summary;
  private final @ModelField(targetType="Int") List<Integer> similar_games;
  private final @ModelField(targetType="Boolean") Boolean complete;
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
  
  public Double getAggregatedRating() {
      return aggregated_rating;
  }
  
  public Integer getCategory() {
      return category;
  }
  
  public String getCoverUrl() {
      return cover_url;
  }
  
  public Temporal.Date getFirstReleaseDate() {
      return first_release_date;
  }
  
  public List<Integer> getGenres() {
      return genres;
  }
  
  public String getName() {
      return name;
  }
  
  public String getSummary() {
      return summary;
  }
  
  public List<Integer> getSimilarGames() {
      return similar_games;
  }
  
  public Boolean getComplete() {
      return complete;
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
  
  private Game(String id, Double aggregated_rating, Integer category, String cover_url, Temporal.Date first_release_date, List<Integer> genres, String name, String summary, List<Integer> similar_games, Boolean complete, ModelReference<User> user) {
    this.id = id;
    this.aggregated_rating = aggregated_rating;
    this.category = category;
    this.cover_url = cover_url;
    this.first_release_date = first_release_date;
    this.genres = genres;
    this.name = name;
    this.summary = summary;
    this.similar_games = similar_games;
    this.complete = complete;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Game game = (Game) obj;
      return ObjectsCompat.equals(getId(), game.getId()) &&
              ObjectsCompat.equals(getAggregatedRating(), game.getAggregatedRating()) &&
              ObjectsCompat.equals(getCategory(), game.getCategory()) &&
              ObjectsCompat.equals(getCoverUrl(), game.getCoverUrl()) &&
              ObjectsCompat.equals(getFirstReleaseDate(), game.getFirstReleaseDate()) &&
              ObjectsCompat.equals(getGenres(), game.getGenres()) &&
              ObjectsCompat.equals(getName(), game.getName()) &&
              ObjectsCompat.equals(getSummary(), game.getSummary()) &&
              ObjectsCompat.equals(getSimilarGames(), game.getSimilarGames()) &&
              ObjectsCompat.equals(getComplete(), game.getComplete()) &&
              ObjectsCompat.equals(getUser(), game.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), game.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), game.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getAggregatedRating())
      .append(getCategory())
      .append(getCoverUrl())
      .append(getFirstReleaseDate())
      .append(getGenres())
      .append(getName())
      .append(getSummary())
      .append(getSimilarGames())
      .append(getComplete())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Game {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("aggregated_rating=" + String.valueOf(getAggregatedRating()) + ", ")
      .append("category=" + String.valueOf(getCategory()) + ", ")
      .append("cover_url=" + String.valueOf(getCoverUrl()) + ", ")
      .append("first_release_date=" + String.valueOf(getFirstReleaseDate()) + ", ")
      .append("genres=" + String.valueOf(getGenres()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("summary=" + String.valueOf(getSummary()) + ", ")
      .append("similar_games=" + String.valueOf(getSimilarGames()) + ", ")
      .append("complete=" + String.valueOf(getComplete()) + ", ")
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
  public static Game justId(String id) {
    return new Game(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      aggregated_rating,
      category,
      cover_url,
      first_release_date,
      genres,
      name,
      summary,
      similar_games,
      complete,
      user);
  }
  public interface BuildStep {
    Game build();
    BuildStep id(String id);
    BuildStep aggregatedRating(Double aggregatedRating);
    BuildStep category(Integer category);
    BuildStep coverUrl(String coverUrl);
    BuildStep firstReleaseDate(Temporal.Date firstReleaseDate);
    BuildStep genres(List<Integer> genres);
    BuildStep name(String name);
    BuildStep summary(String summary);
    BuildStep similarGames(List<Integer> similarGames);
    BuildStep complete(Boolean complete);
    BuildStep user(User user);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Double aggregated_rating;
    private Integer category;
    private String cover_url;
    private Temporal.Date first_release_date;
    private List<Integer> genres;
    private String name;
    private String summary;
    private List<Integer> similar_games;
    private Boolean complete;
    private ModelReference<User> user;
    public Builder() {
      
    }
    
    private Builder(String id, Double aggregated_rating, Integer category, String cover_url, Temporal.Date first_release_date, List<Integer> genres, String name, String summary, List<Integer> similar_games, Boolean complete, ModelReference<User> user) {
      this.id = id;
      this.aggregated_rating = aggregated_rating;
      this.category = category;
      this.cover_url = cover_url;
      this.first_release_date = first_release_date;
      this.genres = genres;
      this.name = name;
      this.summary = summary;
      this.similar_games = similar_games;
      this.complete = complete;
      this.user = user;
    }
    
    @Override
     public Game build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Game(
          id,
          aggregated_rating,
          category,
          cover_url,
          first_release_date,
          genres,
          name,
          summary,
          similar_games,
          complete,
          user);
    }
    
    @Override
     public BuildStep aggregatedRating(Double aggregatedRating) {
        this.aggregated_rating = aggregatedRating;
        return this;
    }
    
    @Override
     public BuildStep category(Integer category) {
        this.category = category;
        return this;
    }
    
    @Override
     public BuildStep coverUrl(String coverUrl) {
        this.cover_url = coverUrl;
        return this;
    }
    
    @Override
     public BuildStep firstReleaseDate(Temporal.Date firstReleaseDate) {
        this.first_release_date = firstReleaseDate;
        return this;
    }
    
    @Override
     public BuildStep genres(List<Integer> genres) {
        this.genres = genres;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep summary(String summary) {
        this.summary = summary;
        return this;
    }
    
    @Override
     public BuildStep similarGames(List<Integer> similarGames) {
        this.similar_games = similarGames;
        return this;
    }
    
    @Override
     public BuildStep complete(Boolean complete) {
        this.complete = complete;
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
    private CopyOfBuilder(String id, Double aggregatedRating, Integer category, String coverUrl, Temporal.Date firstReleaseDate, List<Integer> genres, String name, String summary, List<Integer> similarGames, Boolean complete, ModelReference<User> user) {
      super(id, aggregated_rating, category, cover_url, first_release_date, genres, name, summary, similar_games, complete, user);
      
    }
    
    @Override
     public CopyOfBuilder aggregatedRating(Double aggregatedRating) {
      return (CopyOfBuilder) super.aggregatedRating(aggregatedRating);
    }
    
    @Override
     public CopyOfBuilder category(Integer category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder coverUrl(String coverUrl) {
      return (CopyOfBuilder) super.coverUrl(coverUrl);
    }
    
    @Override
     public CopyOfBuilder firstReleaseDate(Temporal.Date firstReleaseDate) {
      return (CopyOfBuilder) super.firstReleaseDate(firstReleaseDate);
    }
    
    @Override
     public CopyOfBuilder genres(List<Integer> genres) {
      return (CopyOfBuilder) super.genres(genres);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder summary(String summary) {
      return (CopyOfBuilder) super.summary(summary);
    }
    
    @Override
     public CopyOfBuilder similarGames(List<Integer> similarGames) {
      return (CopyOfBuilder) super.similarGames(similarGames);
    }
    
    @Override
     public CopyOfBuilder complete(Boolean complete) {
      return (CopyOfBuilder) super.complete(complete);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  

  public static class GameIdentifier extends ModelIdentifier<Game> {
    private static final long serialVersionUID = 1L;
    public GameIdentifier(String id) {
      super(id);
    }
  }
  
}

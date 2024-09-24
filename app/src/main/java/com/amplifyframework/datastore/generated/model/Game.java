package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.temporal.Temporal;

import androidx.core.util.ObjectsCompat;

import java.util.Objects;
import java.util.List;

/** This is an auto generated class representing the Game type in your schema. */
public final class Game implements Model {
  private final String id;
  private final String name;
  private final String description;
  private final String cover_art;
  private final Temporal.Date release_date;
  private final Double aggregated_rating;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getCoverArt() {
      return cover_art;
  }
  
  public Temporal.Date getReleaseDate() {
      return release_date;
  }
  
  public Double getAggregatedRating() {
      return aggregated_rating;
  }
  
  private Game(String id, String name, String description, String cover_art, Temporal.Date release_date, Double aggregated_rating) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.cover_art = cover_art;
    this.release_date = release_date;
    this.aggregated_rating = aggregated_rating;
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
              ObjectsCompat.equals(getName(), game.getName()) &&
              ObjectsCompat.equals(getDescription(), game.getDescription()) &&
              ObjectsCompat.equals(getCoverArt(), game.getCoverArt()) &&
              ObjectsCompat.equals(getReleaseDate(), game.getReleaseDate()) &&
              ObjectsCompat.equals(getAggregatedRating(), game.getAggregatedRating());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getCoverArt())
      .append(getReleaseDate())
      .append(getAggregatedRating())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      cover_art,
      release_date,
      aggregated_rating);
  }
  public interface BuildStep {
    Game build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep description(String description);
    BuildStep coverArt(String coverArt);
    BuildStep releaseDate(Temporal.Date releaseDate);
    BuildStep aggregatedRating(Double aggregatedRating);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String description;
    private String cover_art;
    private Temporal.Date release_date;
    private Double aggregated_rating;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String description, String cover_art, Temporal.Date release_date, Double aggregated_rating) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.cover_art = cover_art;
      this.release_date = release_date;
      this.aggregated_rating = aggregated_rating;
    }
    
    @Override
     public Game build() {
        
        return new Game(
          id,
          name,
          description,
          cover_art,
          release_date,
          aggregated_rating);
    }
    
    @Override
     public BuildStep id(String id) {
        this.id = id;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep coverArt(String coverArt) {
        this.cover_art = coverArt;
        return this;
    }
    
    @Override
     public BuildStep releaseDate(Temporal.Date releaseDate) {
        this.release_date = releaseDate;
        return this;
    }
    
    @Override
     public BuildStep aggregatedRating(Double aggregatedRating) {
        this.aggregated_rating = aggregatedRating;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String coverArt, Temporal.Date releaseDate, Double aggregatedRating) {
      super(id, name, description, cover_art, release_date, aggregated_rating);
      
    }
    
    @Override
     public CopyOfBuilder id(String id) {
      return (CopyOfBuilder) super.id(id);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder coverArt(String coverArt) {
      return (CopyOfBuilder) super.coverArt(coverArt);
    }
    
    @Override
     public CopyOfBuilder releaseDate(Temporal.Date releaseDate) {
      return (CopyOfBuilder) super.releaseDate(releaseDate);
    }
    
    @Override
     public CopyOfBuilder aggregatedRating(Double aggregatedRating) {
      return (CopyOfBuilder) super.aggregatedRating(aggregatedRating);
    }
  }
  
}

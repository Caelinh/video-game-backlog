package com.amplifyframework.datastore.generated.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amplifyframework.core.model.ModelPath;
import com.amplifyframework.core.model.PropertyPath;

/** This is an auto generated class representing the ModelPath for the User type in your schema. */
public final class UserPath extends ModelPath<User> {
  private GamePath games;
  UserPath(@NonNull String name, @NonNull Boolean isCollection, @Nullable PropertyPath parent) {
    super(name, isCollection, parent, User.class);
  }
  
  public synchronized GamePath getGames() {
    if (games == null) {
      games = new GamePath("games", true, this);
    }
    return games;
  }
}

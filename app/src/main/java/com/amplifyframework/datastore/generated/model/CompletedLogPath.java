package com.amplifyframework.datastore.generated.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amplifyframework.core.model.ModelPath;
import com.amplifyframework.core.model.PropertyPath;

/** This is an auto generated class representing the ModelPath for the CompletedLog type in your schema. */
public final class CompletedLogPath extends ModelPath<CompletedLog> {
  private UserPath user;
  CompletedLogPath(@NonNull String name, @NonNull Boolean isCollection, @Nullable PropertyPath parent) {
    super(name, isCollection, parent, CompletedLog.class);
  }
  
  public synchronized UserPath getUser() {
    if (user == null) {
      user = new UserPath("user", false, this);
    }
    return user;
  }
}

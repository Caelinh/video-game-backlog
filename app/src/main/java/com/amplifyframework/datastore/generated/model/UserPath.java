package com.amplifyframework.datastore.generated.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amplifyframework.core.model.ModelPath;
import com.amplifyframework.core.model.PropertyPath;

/** This is an auto generated class representing the ModelPath for the User type in your schema. */
public final class UserPath extends ModelPath<User> {
  private BackLogPath backlog;
  private CompletedLogPath completedlog;
  UserPath(@NonNull String name, @NonNull Boolean isCollection, @Nullable PropertyPath parent) {
    super(name, isCollection, parent, User.class);
  }
  
  public synchronized BackLogPath getBacklog() {
    if (backlog == null) {
      backlog = new BackLogPath("backlog", false, this);
    }
    return backlog;
  }
  
  public synchronized CompletedLogPath getCompletedlog() {
    if (completedlog == null) {
      completedlog = new CompletedLogPath("completedlog", false, this);
    }
    return completedlog;
  }
}

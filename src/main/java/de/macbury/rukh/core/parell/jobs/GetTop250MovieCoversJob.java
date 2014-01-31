package de.macbury.rukh.core.parell.jobs;

import java.util.List;
import com.moviejukebox.imdbapi.ImdbApi;
import com.moviejukebox.imdbapi.model.ImdbList;
import de.macbury.rukh.core.parell.Job;

public class GetTop250MovieCoversJob extends Job<List> {

  public GetTop250MovieCoversJob() {
    super(List.class);
  }

  @Override
  public List perform() {
    return ImdbApi.getTop250();
  }

}

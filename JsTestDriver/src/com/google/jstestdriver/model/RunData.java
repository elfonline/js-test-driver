/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.jstestdriver.model;

import com.google.common.collect.Lists;
import com.google.jstestdriver.FileInfo;
import com.google.jstestdriver.ResponseStream;

import java.util.List;
import java.util.Set;

/**
 * An immutable container, for the necessary data and responses from a JsTestDriver run.
 * @author corysmith@google.com (Cory Smith)
 *
 */
public class RunData {
  private final List<ResponseStream> responses;
  private final Set<FileInfo> fileSet;

  public RunData(Set<FileInfo> fileSet, List<ResponseStream> responses) {
    this.fileSet = fileSet;
    this.responses = responses;
  }

  public RunData recordResponse(ResponseStream responseStream) {
    final List<ResponseStream> newResponses = Lists.newLinkedList(responses);
    newResponses.add(responseStream);
    return new RunData(fileSet, newResponses);
  }

  public RunData aggregateResponses(RunData runData) {
    final List<ResponseStream> newResponses = Lists.newLinkedList(responses);
    newResponses.addAll(runData.responses);
    return new RunData(fileSet, newResponses);
  }

  public Set<FileInfo> getFileSet() {
    return fileSet;
  }

  public void finish() {
    for (ResponseStream response : responses) {
      response.finish();
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fileSet == null) ? 0 : fileSet.hashCode());
    result = prime * result + ((responses == null) ? 0 : responses.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RunData other = (RunData) obj;
    if (fileSet == null) {
      if (other.fileSet != null) return false;
    } else if (!fileSet.equals(other.fileSet)) return false;
    if (responses == null) {
      if (other.responses != null) return false;
    } else if (!responses.equals(other.responses)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RunData [responses=" + responses + ", fileSet=" + fileSet + "]";
  }
  
  
  
}
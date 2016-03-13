/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.CharFilter;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TextCompletionProvider {
  Key<TextCompletionProvider> COMPLETING_TEXT_FIELD_KEY = Key.create("COMPLETING_TEXT_FIELD_KEY");

  @Nullable
  String getPrefix(@NotNull String text, int offset);

  @NotNull
  CompletionResultSet applyPrefixMatcher(@NotNull CompletionResultSet result, @NotNull String prefix);

  @Nullable
  CharFilter.Result acceptChar(char c);

  void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull String prefix, @NotNull CompletionResultSet result);

  @Nullable
  static TextCompletionProvider getProvider(@NotNull PsiFile file) {
    TextCompletionProvider provider = file.getUserData(COMPLETING_TEXT_FIELD_KEY);

    if (provider == null || (DumbService.isDumb(file.getProject()) && !DumbService.isDumbAware(provider))) {
      return null;
    }
    return provider;
  }
}

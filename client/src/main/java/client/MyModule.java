/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ApplicationUtils.class).in(Scopes.SINGLETON);
        binder.bind(GameUtils.class).in(Scopes.SINGLETON);
        binder.bind(NamePromptCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
        binder.bind(SinglePlayerLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AnswerRevealCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ComparisonQuestionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ExitScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EstimateQuestionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(HomeScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(InfoScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(IntermediateLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MCQuestionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MPFinalLeaderboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(NamePromptCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SimilarQuestionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoomCtrl.class).in(Scopes.SINGLETON);
    }
}
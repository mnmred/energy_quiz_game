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

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Injector;

import client.scenes.MainCtrl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        var home =
                FXML.load(HomeScreenCtrl.class, "client", "scenes", "HomeScreen.fxml");
        var sp =
                FXML.load(SinglePlayerLeaderboardCtrl.class, "client", "scenes", "SinglePlayerLeaderboard.fxml");
        var exit =
                FXML.load(ExitScreenCtrl.class, "client", "scenes", "ExitScreen.fxml");
        var waiting =
                FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoom.fxml");
        var edit = 
                FXML.load(EditScreenCtrl.class, "client", "scenes", "EditScreen.fxml");
        var intermediate =
                FXML.load(IntermediateLeaderboardCtrl.class, "client", "scenes", "IntermediateLeaderboard.fxml");
        var answerReveal =
                FXML.load(AnswerRevealCtrl.class, "client", "scenes", "AnswerReveal.fxml");
        var MPFinal =
                FXML.load(MPFinalLeaderboardCtrl.class, "client", "scenes", "MPFinalLeaderboard.fxml");
        var info =
                FXML.load(InfoScreenCtrl.class, "client", "scenes", "InfoScreen.fxml");
        var prompt =
                FXML.load(NamePromptCtrl.class, "client", "scenes", "NamePrompt.fxml");
        var question =
                FXML.load(ComparisonQuestionCtrl.class, "client", "scenes", "ComparisonQuestion.fxml");
        var editActivity =
                FXML.load(EditActivityCtrl.class, "client", "scenes", "EditActivity.fxml");
        var estimateQuestion =
                FXML.load(EstimateQuestionCtrl.class, "client", "scenes", "EstimateQuestion.fxml");
        var MCQuestion =
                FXML.load(MCQuestionCtrl.class, "client", "scenes", "MCQuestion.fxml");
        var similarQuestion =
                FXML.load(SimilarQuestionCtrl.class, "client", "scenes", "SimilarQuestion.fxml");


        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        INJECTOR.getInstance(ApplicationUtils.class);
        ServerUtils s = INJECTOR.getInstance(ServerUtils.class);
        GameUtils g = INJECTOR.getInstance(GameUtils.class);

        mainCtrl.initialize(primaryStage,
                home,
                sp,
                exit,
                waiting,
                edit,
                intermediate,
                answerReveal,
                MPFinal,
                info,
                prompt,
                question,
                editActivity,
                estimateQuestion,
                MCQuestion,
                similarQuestion,
                s,
                g);
    }
}
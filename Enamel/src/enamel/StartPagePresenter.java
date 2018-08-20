package enamel;

public class StartPagePresenter {

	private final StartPageView view;
	private final ScenarioEditorPresenter parent;
	
	public StartPagePresenter(StartPageView view, ScenarioEditorPresenter parent) {
		this.view = view;
		this.parent = parent;
		attachEvents();
	}

	private void attachEvents() {
    	view.createStoryBtn.setOnAction(e -> parent.displayPage(Page.STORY));
    	view.createQuizBtn.setOnAction(e -> parent.displayPage(Page.QUIZ));
    	view.createBrailleBtn.setOnAction(e -> parent.displayPage(Page.BRAILLE));		
	}

}

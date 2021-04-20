package ntut.csie.sslab.kanban.usecase.card.edit;//package ntut.csie.kanban.useCase.card.edit;
//
//import ntut.csie.sslab.kanban.adapter.presenter.card.*;
//import ntut.csie.sslab.kanban.entity.model.card.Card;
//import ntut.csie.sslab.kanban.useCase.card.CardRepository;
//import ntut.csie.sslab.kanban.useCase.category.CategoryRepository;
//import ntut.csie.sslab.kanban.useCase.card.edit.category.EditCardCategoryInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.category.EditCardCategoryUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.category.EditCardCategoryUseCaseImpl;
//import ntut.csie.sslab.kanban.useCase.card.edit.deadline.EditCardDeadlineInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.deadline.EditCardDeadlineUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.deadline.EditCardDeadlineUseCaseImpl;
//import ntut.csie.sslab.kanban.useCase.card.edit.description.ChangeCardDescriptionInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.description.ChangeCardDescriptionUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.description.ChangeCardDescriptionUseCaseImpl;
//import ntut.csie.sslab.kanban.useCase.card.edit.estimate.EditCardEstimateInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.estimate.EditCardEstimateUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.estimate.EditCardEstimateUseCaseImpl;
//import ntut.csie.sslab.kanban.useCase.card.edit.notes.EditCardNotesInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.notes.EditCardNotesUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.notes.EditCardNotesUseCaseImpl;
//import ntut.csie.sslab.kanban.useCase.card.edit.type.EditCardTypeInput;
//import ntut.csie.sslab.kanban.useCase.card.edit.type.EditCardTypeUseCase;
//import ntut.csie.sslab.kanban.useCase.card.edit.type.EditCardTypeUseCaseImpl;
//
//public class EditCardUseCaseImpl implements EditCardUseCase, EditCardInput {
//	private CategoryRepository categoryRepository;
//	private CardRepository cardRepository;
//
//	private String cardId;
//	private String description;
//	private String categoryId;
//	private String userId;
//	private String estimate;
//	private String notes;
//	private String deadline;
//	private String type;
//
//	public EditCardUseCaseImpl(CategoryRepository categoryRepository, CardRepository cardRepository) {
//		this.categoryRepository = categoryRepository;
//		this.cardRepository = cardRepository;
//	}
//
//	@Override
//	public void execute(EditCardInput input, EditCardOutput output) {
//		String cardId = input.getCardId();
//
//		Card card = cardRepository.findById(cardId);
//		card.setUserId(input.getUserId());
//
//		changeDescription(input.getDescription());
//		changeDeadline(input.getDeadline());
//		changeEstimate(input.getEstimate());
//		changeNotes(input.getNotes());
//		changeType(input.getType());
//		changeCategory(input.getCategoryId());
//
//		output.setCardId(cardId);
//	}
//
//	@Override
//	public String getCardId() {
//		return cardId;
//	}
//
//	@Override
//	public void setCardId(String cardId) {
//		this.cardId = cardId;
//	}
//
//	@Override
//	public String getDescription() {
//		return description;
//	}
//
//	@Override
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	@Override
//	public String getCategoryId() {
//		return categoryId;
//	}
//
//	@Override
//	public void setCategoryId(String categoryId) {
//		this.categoryId = categoryId;
//	}
//
//	@Override
//	public String getUserId() {
//		return userId;
//	}
//
//	@Override
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
//	@Override
//	public String getEstimate() {
//		return estimate;
//	}
//
//	@Override
//	public void setEstimate(String estimate) {
//		this.estimate = estimate;
//	}
//
//	@Override
//	public String getNotes() {
//		return notes;
//	}
//
//	@Override
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}
//
//	@Override
//	public String getDeadline() {
//		return deadline;
//	}
//
//	@Override
//	public void setDeadline(String deadline) {
//		this.deadline = deadline;
//	}
//
//	@Override
//	public String getType() {
//		return type;
//	}
//
//	@Override
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	private void changeDescription(String description) {
//		ChangeCardDescriptionUseCase editCardDescriptionUseCase = new ChangeCardDescriptionUseCaseImpl(cardRepository);
//
//		ChangeCardDescriptionInput input = (ChangeCardDescriptionInput)editCardDescriptionUseCase;
//		input.setCardId(cardId);
//		input.setNewDescription(description);
//
//		ChangeCardDescriptionPresenter presenter = new ChangeCardDescriptionPresenter();
//		editCardDescriptionUseCase.execute(input, presenter);
//	}
//
//	private void changeCategory(String categoryId) {
//		EditCardCategoryUseCase editCardCategoryUseCase = new EditCardCategoryUseCaseImpl(cardRepository);
//
//		EditCardCategoryInput input = (EditCardCategoryInput)editCardCategoryUseCase;
//		input.setCardId(cardId);
//		input.setCategoryId(categoryId);
//
//		ChangeCardCategoryPresenter presenter = new ChangeCardCategoryPresenter();
//		editCardCategoryUseCase.execute(input, presenter);
//	}
//
//	private void changeDeadline(String deadline) {
//		EditCardDeadlineUseCase editCardDeadlineUseCase = new EditCardDeadlineUseCaseImpl(cardRepository);
//
//		EditCardDeadlineInput input = (EditCardDeadlineInput)editCardDeadlineUseCase;
//		input.setCardId(cardId);
//		input.setDeadline(deadline);
//
//		ChangeCardDeadlinePresenter presenter = new ChangeCardDeadlinePresenter();
//		editCardDeadlineUseCase.execute(input, presenter);
//	}
//
//	private void changeEstimate(String estimate) {
//		EditCardEstimateUseCase editCardEstimateUseCase = new EditCardEstimateUseCaseImpl(cardRepository);
//
//		EditCardEstimateInput input = (EditCardEstimateInput)editCardEstimateUseCase;
//		input.setCardId(cardId);
//		input.setEstimate(estimate);
//
//		ChangeCardEstimatePresenter presenter = new ChangeCardEstimatePresenter();
//		editCardEstimateUseCase.execute(input, presenter);
//	}
//
//	private void changeNotes(String notes) {
//		EditCardNotesUseCase editCardNotesUseCase = new EditCardNotesUseCaseImpl(cardRepository);
//
//		EditCardNotesInput input = (EditCardNotesInput)editCardNotesUseCase;
//		input.setCardId(cardId);
//		input.setNotes(notes);
//
//		ChangeCardNotesPresenter presenter = new ChangeCardNotesPresenter();
//		editCardNotesUseCase.execute(input, presenter);
//	}
//
//	private void changeType(String type) {
//		EditCardTypeUseCase editCardTypeUseCase = new EditCardTypeUseCaseImpl(cardRepository);
//
//		EditCardTypeInput input = (EditCardTypeInput) editCardTypeUseCase;
//		input.setCardId(cardId);
//		input.setType(type);
//
//		ChangeCardTypePresenter presenter = new ChangeCardTypePresenter();
//		editCardTypeUseCase.execute(input, presenter);
//	}
//
//
//}

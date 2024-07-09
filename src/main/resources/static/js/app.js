document.addEventListener("DOMContentLoaded", function() {

  /**
   * Form Select
   */
  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = 1;

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep++;
          this.updateForm();
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep--;
          this.updateForm();
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      // TODO: Validation

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
      this.$step.parentElement.hidden = this.currentStep >= 5;

      // Update summary
      this.updateSummary();
    }

    /**
     * Update summary based on form data
     */
    updateSummary() {
      console.log(this.getCity() + "cos napisalem");

      const summaryContainer = document.querySelector(".donation-summary-before-submit");
      if (summaryContainer) {
        const categories = this.getSelectedCategories();
        const institution = this.getSelectedInstitution();
        const quantity = this.getQuantity();
        const street = this.getStreet();
        const city = this.getCity();
        const zipCode = this.getZipCode();
        const pickUpDate = this.getPickUpDate();
        const pickUpTime = this.getPickUpTime();
        const pickUpComment = this.getPickUpComment();

        summaryContainer.querySelector(".summary-item-categories span").innerText = categories;
        summaryContainer.querySelector(".summary-item-institution span").innerText = institution;
        summaryContainer.querySelector(".summary-item-quantity span").innerText = quantity;
        summaryContainer.querySelector(".summary-item-address span").innerText = `${street}, ${city}, ${zipCode}`;
        summaryContainer.querySelector(".summary-item-date span").innerText = pickUpDate;
        summaryContainer.querySelector(".summary-item-time span").innerText = pickUpTime;
        summaryContainer.querySelector(".summary-item-comment p").innerText = pickUpComment;
      }
    }

    /**
     * Helper methods to get form values
     */
    getSelectedCategories() {
      const selectedCategories = [];
      const categoryCheckboxes = document.querySelectorAll("input[name='categories']:checked");
      categoryCheckboxes.forEach(checkbox => {
        selectedCategories.push(checkbox.dataset.name);
      });
      return selectedCategories.join(", ");
    }

    getSelectedInstitution() {
      const institutionSelect = document.querySelector("select[name='institution']");
      return institutionSelect.options[institutionSelect.selectedIndex].text;
    }

    getQuantity() {
      const quantityInput = document.querySelector("input[name='quantity']");
      return quantityInput.value;
    }

    getStreet() {
      const streetInput = document.querySelector("input[name='street']");
      return streetInput.value;
    }

    getCity() {
      const cityInput = document.querySelector("input[name='city']");
      return cityInput.value;
    }

    getZipCode() {
      const zipCodeInput = document.querySelector("input[name='zipCode']");
      return zipCodeInput.value;
    }

    getPickUpDate() {
      const pickUpDateInput = document.querySelector("input[name='pickUpDate']");
      return pickUpDateInput.value;
    }

    getPickUpTime() {
      const pickUpTimeInput = document.querySelector("input[name='pickUpTime']");
      return pickUpTimeInput.value;
    }

    getPickUpComment() {
      const pickUpCommentInput = document.querySelector("textarea[name='pickUpComment']");
      return pickUpCommentInput.value;
    }

  }
  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }
});

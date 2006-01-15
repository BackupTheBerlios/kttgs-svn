# The methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  def self.append_features(controller) #:nodoc:
    controller.ancestors.include?(ActionController::Base) ? controller.add_template_helper(self) : super
  end
  
  def create_steps_number
     "Four"
  end
  
  def version
     "v0.1pre1"
  end 
end

module Console
  class Controller

    def initialize(servlet_response)
      @servlet_response = servlet_response
    end

    def index
      render "console/index.html"
    end

    def render(template_path)
      template = Java::ClasspathHelper.read_from_classpath template_path
      html = ERB.new(template).result(binding)
      @servlet_response.get_output_stream.print html
    end

    def registry
      Java::ApplicationRegistry.registry
    end

  end
end

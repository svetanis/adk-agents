# ADK Agents - Multi-Agent AI System

A collection of sample agents built with **Google's Agent Development Kit (ADK)** in Java. This repository demonstrates advanced agent orchestration patterns including sequential pipelines, parallel execution, and hierarchical delegation.

## 📋 Overview

**ADK Agents** showcases two powerful multi-agent applications:

1. **Tutor Agent System** - An AI-powered educational tutoring platform
2. **Blogger Agent System** - An autonomous content creation and refinement pipeline

Both systems leverage Google's Gemini 2.5 Flash model and demonstrate sophisticated agent coordination patterns.

---

## 🎯 Project Features

✅ **Multi-Agent Orchestration** - Sequential, parallel, and hierarchical agent patterns  
✅ **Specialized AI Agents** - Domain-specific tutors and content specialists  
✅ **Web Interface** - Built-in ADK Web Server for interactive agent interaction  
✅ **Google Search Integration** - Real-time information retrieval for agents  
✅ **Configuration-Driven Design** - YAML-based agent configuration  
✅ **Modular Architecture** - Reusable agent context and configuration framework  
✅ **Educational Focus** - Demonstrates best practices in agent design  
✅ **Production-Ready** - Following Java best practices with dependency injection

---

## 🏗️ Repository Structure

```
svetanis/adk-agents/
├── src/main/java/com/svetanis/agents/
│   ├── adk/                          # Core ADK framework utilities
│   │   ├── AgentConfig.java          # Agent configuration model
│   │   ├── AgentContext.java         # Agent context builder
│   │   ├── AgentConfigProvider.java  # Configuration provider
│   │   └── LlmAgentProvider.java     # LLM agent factory
│   │
│   ├── tutor/                        # Educational tutoring system
│   │   ├── TutorApp.java             # Application entry point
│   │   ├── TutorAgent.java           # Root tutor orchestrator
│   │   └── README.md                 # Detailed tutor documentation
│   │
│   └── blogger/                      # Content creation system
│       ├── BloggerApp.java           # Application entry point
│       ├── BloggerRootAgent.java     # Root orchestrator
│       ├── BlogPipeline.java         # Sequential pipeline
│       ├── ResearchTeam.java         # Parallel researchers
│       ├── BlogRefinementLoop.java   # Iterative refinement
│       └── [Additional agent classes]
│
├── src/main/resources/
│   ├── tutor/                        # Tutor agent YAML configurations
│   │   ├── root_tutor_agent.yaml
│   │   ├── code_tutor_agent.yaml
│   │   ├── math_tutor_agent.yaml
│   │   └── science_tutor_agent.yaml
│   │
│   └── blogger/                      # Blogger agent YAML configurations
│       ├── root-agent.yaml
│       ├── tech-researcher.yaml
│       ├── health-researcher.yaml
│       ├── finance-researcher.yaml
│       ├── aggregator-agent.yaml
│       ├── writer-agent.yaml
│       ├── editor-agent.yaml
│       ├── critic-agent.yaml
│       ├── refiner-agent.yaml
│       └── presenter-agent.yaml
│
├── pom.xml                           # Maven configuration
├── .gitignore
└── README.md                         # This file
```

---

## 🎓 Tutor Agent System

### Overview
An AI-powered educational system providing personalized tutoring across **Code, Math, and Science** domains. Inspired by Google's 5 Days of AI Course, it demonstrates multi-agent delegation patterns.

### Architecture
```
Student Question
    ↓
Root Tutor Agent (dispatcher)
    ↓
Question Classification
    ├─→ Code Topics → Code Tutor (NullPointer)
    ├─→ Math Topics → Math Tutor (Sigma)
    └─→ Science Topics → Science Tutor (Atom)
    ↓
Specialized Guided Learning Response
```

### Components

#### TutorApp
- **Role:** Application entry point
- **Command:** `mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.tutor.TutorApp`
- **Function:** Initializes web server hosting the root tutor agent

#### TutorAgent
- **Role:** Root orchestrator
- **Function:**  
  - Creates GoogleSearchTool for all tutors  
  - Instantiates three specialized tutor agents  
  - Registers them as tools for routing

#### Specialized Tutors

| Tutor | Persona | Specialty | Method |
|-------|---------|-----------|--------|
| **Code Tutor** | NullPointer | Programming | Guided learning with logic-first approach |
| **Math Tutor** | Sigma | Mathematics | Socratic questioning and hints |
| **Science Tutor** | Atom | Physics & Chemistry | Knowledge-level assessment + analogies |

### Key Features

✅ **Personalized Routing** - Intelligent question classification  
✅ **Guided Learning** - Focus on understanding, not just answers  
✅ **Socratic Method** - Interactive questioning approach  
✅ **Specialized Personalities** - Each tutor has unique teaching style  
✅ **Google Search Integration** - Access to current information  
✅ **Web Interface** - Interactive chat interface  
✅ **Configurable YAML** - Easy to modify agent behaviors

### Teaching Principles

1. **Constructivism** - Students construct their own understanding  
2. **Scaffolding** - Support gradually reduces as competence increases  
3. **Socratic Method** - Learning through questioning  
4. **Growth Mindset** - Emphasis on effort and learning process  
5. **Immediate Feedback** - Positive reinforcement  
6. **Chunked Learning** - Complex concepts broken into digestible pieces  
7. **Analogies** - Abstract concepts explained through relatable examples

### Usage Example

```bash
# Start the tutor application
mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.tutor.TutorApp

# Then ask questions like:
# - "How do I write a function that finds the maximum in an array?"
# - "Explain how to solve quadratic equations"
# - "What is photosynthesis?"
```

### Configuration

Each tutor is configured via YAML:

**root_tutor_agent.yaml** - Routes student questions to specialists  
**code_tutor_agent.yaml** - NullPointer's programming guidance  
**math_tutor_agent.yaml** - Sigma's mathematical assistance  
**science_tutor_agent.yaml** - Atom's scientific explanations

---

## 📰 Blogger Agent System

### Overview
An autonomous multi-stage content creation system that researches topics, aggregates findings, writes articles, refines content, and presents final outputs.

### Architecture

```
Root Agent
    ↓
Blog Pipeline (Sequential)
    ├─→ Stage 1: Research Team (Parallel)
    │   ├── Tech Researcher
    │   ├── Health Researcher
    │   └── Finance Researcher
    ├─→ Stage 2: Aggregator (Sequential)
    ├─→ Stage 3: Writer (Sequential)
    ├─→ Stage 4: Refinement Loop (Optional)
    │   └── Critic → Refiner (up to 2 iterations)
    └─→ Stage 5: Presenter (Sequential)
```

### Components

#### BloggerApp
- **Command:** `mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp`
- **Function:** Initializes the blogger system with optional refinement loop
- **Parameters:** `blogger.refinement.loop` (true/false)

#### BloggerRootAgent
- **Role:** Entry point and tool provider
- **Function:** Routes requests to BlogPipeline

#### BlogPipeline (SequentialAgent)
Orchestrates 5-stage content creation process:
1. **ResearchTeam** (ParallelAgent) - Simultaneous research across 3 domains
2. **Aggregator** - Combines research findings
3. **Writer** - Creates article from aggregated data
4. **Refiner** - Optional iterative refinement (Critic → Refiner loop)
5. **Presenter** - Formats final output

#### Key Agent Classes

- **ResearchTeam** - Runs parallel domain-specific researchers
- **BlogPipeline** - Coordinates sequential stages
- **BlogRefinementLoop** - LoopAgent with iterative critic/refiner pattern
- **BloggerRootAgent** - Root orchestrator

### Multi-Agent Patterns Demonstrated

1. **Parallel Execution** - Research team runs simultaneously  
2. **Sequential Pipeline** - Stages execute in order  
3. **Iterative Refinement** - Loop agent with max iterations  
4. **Tool Integration** - Google Search for research  
5. **Configuration Management** - YAML-based agent setup

### Usage Example

```bash
# Start with refinement loop (default)
mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp

# Or without refinement (faster)
mvn compile exec:java \
  -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp \
  -Dblogger.refinement.loop=false

# Prompt: "Run the daily executive briefing on Tech, Health, and Finance"
```

---

## 🔧 Core Framework (ADK Utilities)

### AgentConfig
Models agent configuration from YAML:
```java
{
  "name": "agent_name",
  "model": "gemini-2.5-flash",
  "description": "Agent description",
  "instruction": "System prompt...",
  "outputKey": "optional_output_key",
  "afterAgentCallback": "optional_callback"
}
```

### AgentContext
Builder pattern for agent setup:
```java
AgentContext ctx = AgentContext.builder()
    .withConfig(config)
    .withTools(tool1, tool2)
    .withSubAgents(subAgent1, subAgent2)
    .build();
```

### LlmAgentProvider
Factory for creating LLM agents from configuration:
```java
// From YAML path
LlmAgent agent = new LlmAgentProvider("path/to/config").get();

// With tools
LlmAgent agent = new LlmAgentProvider("path", tool).get();

// From context
LlmAgent agent = new LlmAgentProvider(context).get();
```

### AgentConfigProvider
Loads YAML configuration:
```java
AgentConfig config = new AgentConfigProvider("blogger/root-agent").get();
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Google ADK SDK
- API credentials for Google Gemini and Search

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/svetanis/adk-agents.git
   cd adk-agents
   ```

2. **Configure environment**
   ```bash
   export GOOGLE_APPLICATION_CREDENTIALS=path/to/credentials.json
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

### Running Applications

#### Tutor System
```bash
mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.tutor.TutorApp
```
Access via web interface at `http://localhost:8080`

#### Blogger System
```bash
mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.blogger.BloggerApp
```
Access via web interface at `http://localhost:8080`

---

## 📚 Technologies & Dependencies

- **Google ADK** (1.0.0-rc.1) - Agent Development Kit core
- **Google Gemini 2.5 Flash** - LLM model
- **Google Search Tool** - Real-time web research
- **Jackson** (2.17.0) - JSON/YAML processing
- **Guava** - Collections and utilities
- **Java 17** - Language version

### Maven Configuration

```xml
<properties>
  <adk.version>1.0.0-rc.1</adk.version>
  <maven.compiler.source>17</maven.compiler.source>
  <maven.compiler.target>17</maven.compiler.target>
  <jackson.version>2.17.0</jackson.version>
</properties>
```

---

## 🎨 Design Patterns

### 1. **Provider Pattern**
Dependency injection using `javax.inject.Provider`:
```java
public class TutorAgent implements Provider<LlmAgent> { ... }
```

### 2. **Delegation Pattern**
Root agent delegates to specialists:
- Root → Code/Math/Science Tutors
- Root → Blog Pipeline

### 3. **Tool Pattern**
Wrap agents as tools for higher-level orchestration:
```java
AgentTool.create(subAgent)
```

### 4. **Builder Pattern**
Fluent configuration:
```java
AgentContext.builder()
    .withConfig(config)
    .withTools(tools)
    .build()
```

### 5. **Factory Pattern**
LlmAgentProvider creates agents from configuration

### 6. **Configuration-Driven Design**
YAML externalization for agent behavior

---

## 🏆 Key Concepts

### Agent Types
- **LlmAgent** - Language model-based agents
- **SequentialAgent** - Runs sub-agents in sequence
- **ParallelAgent** - Runs sub-agents simultaneously
- **LoopAgent** - Iterative execution with max iterations

### Agent Composition
- **Root agents** coordinate multiple sub-agents
- **Sub-agents** wrapped as tools for parent agents
- **Tools** provide agents with capabilities

### Information Flow
1. User input → Root agent  
2. Root agent analyzes and routes to specialists  
3. Specialists process and return results  
4. Results aggregated/refined as needed  
5. Final output returned to user

---

## 📖 Use Cases

### Tutor System
- ✅ High school students learning programming  
- ✅ Undergraduate coursework assistance  
- ✅ Exam preparation  
- ✅ Homeschooling support  
- ✅ Self-paced learning  
- ✅ Supplemental tutoring

### Blogger System
- ✅ Automated research aggregation  
- ✅ Multi-perspective content generation  
- ✅ News briefings  
- ✅ Executive summaries  
- ✅ Content refinement pipelines  
- ✅ Multi-stage editing workflows

---

## 🔮 Future Enhancements

### Tutor System
- Support for additional subjects (English, History, Languages)
- Adaptive difficulty levels
- Progress tracking and analytics
- Problem set generation
- Peer collaboration features
- Video explanations
- LMS integration (Canvas, Blackboard)
- Multi-language support
- Parent/teacher dashboards

### Blogger System
- Additional domain researchers
- Content scheduling
- SEO optimization agents
- Fact-checking agents
- Publishing automation
- Analytics integration
- A/B testing capabilities
- Multi-format output (PDF, HTML, Markdown)

---

## 📝 Configuration Files

### Tutor Resources
Located in `src/main/resources/tutor/`:
- `root_tutor_agent.yaml` - Router/dispatcher  
- `code_tutor_agent.yaml` - Programming tutor  
- `math_tutor_agent.yaml` - Math tutor  
- `science_tutor_agent.yaml` - Science tutor

### Blogger Resources
Located in `src/main/resources/blogger/`:
- `root-agent.yaml` - Entry point  
- `tech-researcher.yaml` - Tech domain  
- `health-researcher.yaml` - Health domain  
- `finance-researcher.yaml` - Finance domain  
- `aggregator-agent.yaml` - Data aggregation  
- `writer-agent.yaml` - Content creation  
- `editor-agent.yaml` - Editing  
- `critic-agent.yaml` - Quality critique  
- `refiner-agent.yaml` - Refinement  
- `presenter-agent.yaml` - Final presentation

---

## 🤝 Contributing

Contributions are welcome! Areas for enhancement:
- Additional agent examples
- Performance optimizations
- Documentation improvements
- New agent patterns
- Testing frameworks

---

## 📄 License

This project is open source and available under MIT License.

---

## 👤 Author

**Svetanis** - https://github.com/svetanis

---

## 🔗 References

- [Google's 5 Days of AI Course](https://www.kaggle.com/learn-guide/5-day-agents)
- [Google ADK Documentation](https://ai.google.dev/)
- [Agent Design Patterns](https://ai.google.dev/agents)

---

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Check existing issues for solutions
- Review agent documentation in respective modules

---

**Last Updated:** 2026-03-28 22:44:27
**Status:** Active Development
**Version:** 1.0-SNAPSHOT

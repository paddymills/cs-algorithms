
SRC_DIR := src
BUILD_DIR := build

SRCS := $(wildcard $(SRC_DIR)/*.java)
BINS := $(patsubst $(SRC_DIR)/%.java,$(BUILD_DIR)/%.class, $(SRCS))

# create build directory if it doesn't exist
$(BUILD_DIR):
	@echo "Creating build directory"
	mkdir "$(BUILD_DIR)"

.PHONY: clean
clean:
ifneq (,$(wildcard $(BUILD_DIR)/*))
	@echo "cleaning build directory"
	rm -r $(BUILD_DIR)/*
endif

# source code compiling
$(BUILD_DIR)/%.class : $(SRC_DIR)/%.java | $(OBJDIR)
	@echo "compiling: ($@) from ($^)"
	javac -d $(BUILD_DIR) $(SRC_DIR)/*.java

test: $(BUILD_DIR)/eTSP.class $(BUILD_DIR)/Point.class $(BUILD_DIR)/Tour.class $(BUILD_DIR)/KMeans.class $(BUILD_DIR)/Progress.class $(BUILD_DIR)/Test.class
	cat tsp_input.txt | java -cp $(BUILD_DIR) Test

tsp: $(BUILD_DIR)/eTSP.class $(BUILD_DIR)/Point.class $(BUILD_DIR)/Tour.class $(BUILD_DIR)/Progress.class
	cat tsp_input.txt | java -cp $(BUILD_DIR) eTSP

# catch-all for launching a binary not expliPoint listed
%: $(BUILD_DIR)/%.class
	java -cp $(BUILD_DIR) $@

#!/bin/bash

# This script auto-generates all documentation contained within OSCARS 1.0.
# Code source files are added to doc/source.
# Deployable and exportable Sphinx output documentation is auto-built and added to doc/build.

# Documentation includes:
    # Manually created user-documentation: located in doc/source/userdoc.
    # Auto-generated code-documentation from Javadoc: located in doc/source/codedoc.

# Required Dependencies:
    # Sphinx
    # Javasphinx
    # Make

# Step 1. Compile all the Javadoc into codedoc directory.
javasphinx-apidoc -o ../doc/source/codedoc .. -u      #format: -o (output directory) <output directory> <input directory> -u (update outdated only)

# Step 2. Build all HTML files from Sphinx source files (reStructuredText files).
make html --directory ../doc/

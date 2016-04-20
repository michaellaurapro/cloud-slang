/*
 * (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package io.cloudslang.lang.entities.bindings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author orius123
 * @since 05/11/14.
 * @version $Id$
 */
public class Input extends InOutParam {

	private static final long serialVersionUID = -2411446962609754342L;

	private boolean required;
	private boolean overridable;

	private Input(InputBuilder inputBuilder) {
		super(inputBuilder.name,
				inputBuilder.value,
				inputBuilder.functionDependencies,
				inputBuilder.systemPropertyDependencies
		);
		this.required = inputBuilder.required;
		this.overridable = inputBuilder.overridable;
	}

	/**
	 * only here to satisfy serialization libraries
	 */
	@SuppressWarnings("unused")
	private Input() {}

	public boolean isRequired() {
		return required;
	}

	public boolean isOverridable() {
		return overridable;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("required", required)
				.append("overridable", overridable)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Input input = (Input) o;

		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(required, input.required)
				.append(overridable, input.overridable)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.appendSuper(super.hashCode())
				.append(required)
				.append(overridable)
				.toHashCode();
	}

	public static class InputBuilder {
		private String name;
		private Value value;
		private boolean required;
		private boolean overridable;
		private Set<ScriptFunction> functionDependencies;
		private Set<String> systemPropertyDependencies;

		public InputBuilder(String name, Serializable serializable) {
			this(name, serializable, false);
		}

		public InputBuilder(String name, Serializable serializable, boolean sensitive) {
			this.name = name;
			this.value = createValue(serializable, sensitive);
			this.required = true;
			this.overridable = true;
			this.functionDependencies = new HashSet<>();
			this.systemPropertyDependencies = new HashSet<>();
		}

		public InputBuilder withRequired(boolean required) {
			this.required = required;
			return this;
		}

		public InputBuilder withOverridable(boolean overridable) {
			this.overridable = overridable;
			return this;
		}

		public InputBuilder withFunctionDependencies(Set<ScriptFunction> functionDependencies) {
			this.functionDependencies = functionDependencies;
			return this;
		}

		public InputBuilder withSystemPropertyDependencies(Set<String> systemPropertyDependencies) {
			this.systemPropertyDependencies = systemPropertyDependencies;
			return this;
		}

		private Value createValue(Serializable serializable, boolean sensitive) {
			return sensitive ? new SensitiveValue(serializable) : new SimpleValue(serializable);
		}

		public Input build() {
			return new Input(this);
		}
	}

}

package com.pkumar7.datastructures;
import java.util.ArrayList;
import java.util.HashMap;

public class TopologicalSort {

	public static void main(String[] args) {
		Solution solution = new Solution();
		int[][] prerequisites = new int[][] {{1,0},{0,2},{2,3},{3,0}};
		boolean status = solution.canFinish(4, prerequisites);
		System.out.println(status);
	}

	static class Solution {
		public boolean canFinish(int numCourses, int[][] prerequisites) {
			Graph graph = buildGraphOfCourses(numCourses, prerequisites);
			return checkCourseOrder(graph.getNodes());
		}

		public Graph buildGraphOfCourses(int numCourses, int[][] prerequisites) {
			Graph graph = new Graph();
			for (int i = 0; i < numCourses; i++) {
				graph.getOrCreateCourse(i);
			}

			for (int[] dependency : prerequisites) {
				int first = dependency[0];
				int second = dependency[1];
				System.out.println(first + " --> " + second);
				graph.addEgde(first, second);
			}

			return graph;
		}

		public boolean checkCourseOrder(ArrayList<Course> courseList) {

			Course[] order = new Course[courseList.size()];

			int endOfList = addNonDependent(order, courseList, 0);
			int toBeProcessed = 0;
			//System.out.println("endOfList " + endOfList);

			while (toBeProcessed < order.length) {
				
				Course course = order[toBeProcessed];
				//There was no course found with 0 dependencies
				if (course == null) {
					return false;
				}

				ArrayList<Course> children = course.getChildren();
				for (Course child : children) {

					child.decrementDependencies();
				}

				System.out.println("************ addNonDependent ********* ");
				endOfList = addNonDependent(order, courseList, endOfList);
				toBeProcessed++;
			}

			return true;

		}

		public int addNonDependent(Course[] order, ArrayList<Course> courseList, int offset) {
			
			for (Course course : courseList) {
				System.out.println("course : " + course.getCourseNumber() + " dep : " + course.getNumberOfDependencies());
				if (course.getNumberOfDependencies() == 0) {
					if(offset == order.length) {
						return offset;
					}
					System.out.println("Adding course : " + course.getCourseNumber());
					if(!course.isVisited()) {
						course.setVisited();
						order[offset] = course;
						offset++;
					}
					
				}
			}
			return offset;
		}
	}

	static class Graph {
		ArrayList<Course> nodes = new ArrayList<Course>();
		HashMap<Integer, Course> map = new HashMap<>();

		public Course getOrCreateCourse(int course) {
			if (!map.containsKey(course)) {
				Course courseObj = new Course(course);
				map.put(course, courseObj);
				nodes.add(courseObj);
			}
			return map.get(course);
		}

		public void addEgde(int src, int dst) {
			Course start = getOrCreateCourse(src);
			Course end = getOrCreateCourse(dst);
			start.addNeighbour(end);
		}

		public ArrayList<Course> getNodes() {
			return nodes;
		}
	}

	static class Course {
		int dependencies;
		ArrayList<Course> list = new ArrayList<>();
		HashMap<Integer, Course> map = new HashMap<>();
		int course;
		boolean isVisited;

		public Course(int courseNumber) {
			course = courseNumber;
		}

		public void addNeighbour(Course child) {
			if (!map.containsKey(child.getCourseNumber())) {
				map.put(child.getCourseNumber(), child);
				list.add(child);
				child.incrementDependencies();
			}
		}
		
		public void setVisited() {
			isVisited = true;
		}
		
		public boolean isVisited() {
			return isVisited;
		}

		public ArrayList<Course> getChildren() {
			return list;
		}

		public void incrementDependencies() {
			dependencies++;
		}

		public void decrementDependencies() {
			dependencies--;
		}

		public int getNumberOfDependencies() {
			return dependencies;
		}

		public int getCourseNumber() {
			return course;
		}

	}

}

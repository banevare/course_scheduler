public class CanFinish {
  static int Tests = 0;

  public boolean canFinish(int numCourses, int[][] prerequisites) {                       // Return true if you can finish all courses. Otherwise, return false.
    final HashSet<Integer> cache = new HashSet<>();                                       // Cache HashMap to not recompute if course can finish 
    final HashMap<Integer, Stack<Integer>> p = prerequisitesMap(prerequisites);           // HashMap to calculate only once all direct dependecies of each course
    for (int i = 0; i < numCourses; i++) {
      HashSet<Integer> path = new HashSet<>();                                            // Path to investigate through DFS
      boolean b = checkPrerequisites(i, path, cache, p);                                  // Check prerequesites of each course
      if(!b) return false;                                                                // If a cycle exists returns false
    }
    return true;                                                                          // Can finish all courses
  }

  public boolean checkPrerequisites (                                                     // Returns false if there exists a cycle. Otherwise, return true.
    final int course, 
    final HashSet<Integer> path,
    final HashSet<Integer> cache, 
    final HashMap<Integer, Stack<Integer>> p ) {                                                 
    if (cache.contains(course))  return true;                                             // If we have calculated course already return true
    if (path.contains(course))   return false;                                            // If a cycle exists return false
    if (!p.containsKey(course)) {                                                         // If course has no dependencies
      cache.add(course);                                                                  // Add to cache HashSet
      return true;                                                                        // Return true as it shows all dependencies are satisfied
    }
    final Stack<Integer> dependencies = p.get(course);                                    // Get dependencies for given course
    path.add(course);                                                                     // Add the current course to hashset
    for (Integer i : dependencies) {                                                      // Loops through each dependency of the course
      if(!checkPrerequisites(i,path, cache, p)) return false;                             // Checking the depencies of the direct dependency of the course
    }
    path.remove(course);                                                                  // Adjust path to match
    cache.add(course);                                                                    // Add course to cache map as a valid a course that is possible to take without problems
    return true;                                                                          // No conflict 
  }
  public HashMap<Integer, Stack<Integer>> prerequisitesMap(final int[][] prerequisites) { // Returns a Map of the direct dependencies of each course
    final HashMap<Integer, Stack<Integer>> p = new HashMap<>();
    for (int i = 0; i < prerequisites.length; i++) {
      final int[] inner = prerequisites[i];                                               // Each pair of course/prerequesites
      final int currCourse = inner[0];                                                    // Current course you want to take
      final int dependency = inner[1];                                                    // Prerequisite in order to take the course
      if(p.containsKey(currCourse)) {
        final Stack<Integer> e = p.get(currCourse);                                       // Existing stack retrieval
        e.push(dependency);                                                               // Push new value to stack
      } else {
        final Stack<Integer> s = new Stack<>();                                           // Create a stack for new key
        s.push(dependency);                                                               // Add dependency to the stack
        p.put(currCourse, s);                                                             // Put course with respected dependencies in map
      }
    }
    return p;
  }
  public void printPrerequisitesMap(HashMap<Integer, Stack<Integer>> p) {                 // Printing Prerequisite Map to confirm realibility
    for (Integer i : p.keySet()) {
      Stack<Integer> e = p.get(i);                                                        // Existing stack retrieval
      for (Integer j : e) {                                                               // Loop through each entry of the stack
        say("Course = ", i , " Dependencies = ", j);      
      }
    }
  }                                                                             
  public void test1() {
    int[][] p = {{0,1},{1,5},{1,2},{2,5},{2,4},{4,0},{4,3}};
    printPrerequisitesMap(prerequisitesMap(p));
    assert(!canFinish(6,p));
    Tests++;
  }
  public void test2() {
    int[][] p = {{1,0}};
    assert(canFinish(2,p));
    Tests++;
  }
  public void test3() {
    int[][] p = {{1,0},{0,1}};
    assert(!canFinish(2,p));
    Tests++;
  }
  public void testPrintPrerequisitesMap() {
    int[][] p = {{1,0},{0,1}};
    printPrerequisitesMap(prerequisitesMap(p));
    Tests++;
  }

  public static void say(Object...O) {
    final StringBuilder b = new StringBuilder();
    for(Object o: O) { b.append(o); b.append(" "); }
    System.err.println(b.toString());
  }
  static public void main(String args[]) { 
    final CanFinish s = new CanFinish();
    s.test1();
    s.test2();
    s.test3();
    // s.testPrintPrerequisitesMap();
    say("Passes:", Tests, "Tests"); 
  }
}

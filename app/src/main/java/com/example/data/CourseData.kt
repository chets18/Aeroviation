package com.example.data

data class Course(
    val id: String,
    val badge: String,
    val title: String,
    val description: String,
    val progress: Float, // 0.0 to 1.0
    val isLocked: Boolean
)

data class Module(
    val id: String,
    val courseId: String,
    val moduleNumber: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)

sealed class LessonContentBlock {
    data class Paragraph(val text: String) : LessonContentBlock()
    data class BulletList(val items: List<String>) : LessonContentBlock()
    data class DataTable(val headers: List<String>, val rows: List<List<String>>) : LessonContentBlock()
    data class DidYouKnow(val title: String, val content: String) : LessonContentBlock()
    data class Timeline(val items: List<TimelineItem>) : LessonContentBlock()
    data class InfoCard(val title: String, val content: String, val icon: String) : LessonContentBlock()
}

data class TimelineItem(val year: String, val event: String, val description: String)

data class SelfCheckQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class ExamQuestion(
    val id: String,
    val type: ExamType,
    val questionText: String,
    val options: List<String>? = null,
    val correctIdx: Int? = null,
    val gradingCriteria: String
)

enum class ExamType { MCQ, WRITTEN }

data class ModuleExam(
    val moduleId: String,
    val examTitle: String,
    val totalTime: String,
    val questions: List<ExamQuestion>
)

data class Lesson(
    val id: String,
    val moduleId: String,
    val lessonNumber: String,
    val title: String,
    val description: String,
    val duration: String,
    val isLocked: Boolean,
    val coreContent: List<LessonContentBlock> = emptyList(),
    val selfCheckList: List<SelfCheckQuestion> = emptyList()
)

object CourseRepository {
    val courses = listOf(
        Course(
            id = "beginner",
            badge = "BEGINNER",
            title = "Foundations of Flight",
            description = "Master the physics of flight, propulsion, and materials. Your launchpad into aerospace.",
            progress = 0.0f,
            isLocked = false
        ),
        Course(
            id = "intermediate",
            badge = "INTERMEDIATE",
            title = "Aeronautics in Depth",
            description = "Fluid mechanics, structural analysis, and UAV systems. Theory meets application.",
            progress = 0.0f,
            isLocked = false
        ),
        Course(
            id = "expert",
            badge = "EXPERT",
            title = "Advanced Aerospace Systems",
            description = "Hypersonics, orbital mechanics, and mission design. Coming soon.",
            progress = 0.0f,
            isLocked = true
        ),
        Course(
            id = "elite",
            badge = "ELITE",
            title = "Beyond the Atmosphere",
            description = "Spacecraft systems, propulsion engineering, and advanced research. Coming soon.",
            progress = 0.0f,
            isLocked = true
        )
    )

    val modules = listOf(
        // BEGINNER MODULES
        Module(
            id = "b_mod0",
            courseId = "beginner",
            moduleNumber = "MODULE 00",
            title = "Introduction to Aviation",
            description = "Welcome to the world of flight"
        ),
        Module(
            id = "b_mod1",
            courseId = "beginner",
            moduleNumber = "MODULE 01",
            title = "Aerodynamics",
            description = "Lift, drag, airfoils, and flow regimes"
        ),
        Module(
            id = "b_mod2",
            courseId = "beginner",
            moduleNumber = "MODULE 02",
            title = "Propulsion",
            description = "Jet engines, rockets, and thrust physics"
        ),
        Module(
            id = "b_mod3",
            courseId = "beginner",
            moduleNumber = "MODULE 03",
            title = "Flight Mechanics and Control",
            description = "Axes, control surfaces, and orbital basics"
        ),
        Module(
            id = "b_mod4",
            courseId = "beginner",
            moduleNumber = "MODULE 04",
            title = "Materials Science and Structure",
            description = "Stress, composites, and thermal challenges"
        ),
        Module(
            id = "b_mod5",
            courseId = "beginner",
            moduleNumber = "MODULE 05",
            title = "Software and Tools",
            description = "Python, CAD, and CFD exploration"
        ),

        // INTERMEDIATE MODULES
        Module(
            id = "i_mod1",
            courseId = "intermediate",
            moduleNumber = "MODULE 01",
            title = "Aerodynamics and Fluid Mechanics",
            description = "Boundary layers, compressibility, and wing theory"
        ),
        Module(
            id = "i_mod2",
            courseId = "intermediate",
            moduleNumber = "MODULE 02",
            title = "Aerospace Structures and Materials",
            description = "Elasticity, buckling, and laminate theory"
        ),
        Module(
            id = "i_mod3",
            courseId = "intermediate",
            moduleNumber = "MODULE 03",
            title = "Flight Mechanics and Dynamics",
            description = "Aircraft performance, stability modes, and path planning"
        ),
        Module(
            id = "i_mod4",
            courseId = "intermediate",
            moduleNumber = "MODULE 04",
            title = "Propulsion and Thermodynamics",
            description = "Brayton cycle analysis, turbomachinery, and BLDC motors"
        ),
        Module(
            id = "i_mod5",
            courseId = "intermediate",
            moduleNumber = "MODULE 05",
            title = "Control Systems and Avionics",
            description = "PID control, sensor fusion, and autopilot architecture"
        )
    )

    val lessons = listOf(
        // BEGINNER MODULE 00 LESSONS
        Lesson(
            id = "b_m0_l1",
            moduleId = "b_mod0",
            lessonNumber = "Lesson 1",
            title = "The Gateway to the Skies",
            description = "Understanding the 'squishy' substance and our journey through it.",
            duration = "8 min",
            isLocked = false,
            coreContent = listOf(
                LessonContentBlock.Paragraph(
                    "Imagine you are standing on a sidewalk during a 40 mph gale. The wind is pushing you around, literally reaching out to grab you. If you were to pick up a book and throw it across the room, you’d understand its motion easily—that's the dynamics of a solid object. But try to scoop up a handful of air and throw it. It just flows through your fingers. Aerodynamics is the science of this \"squishy\" substance—air—and how it interacts with objects moving through it."
                ),
                LessonContentBlock.Paragraph(
                    "### The Story of Flight: From Myths to the Moon"
                ),
                LessonContentBlock.Timeline(
                    listOf(
                        TimelineItem("1783", "Montgolfier Brothers", "Lifted the first human into the air in a hot-air balloon. For the first time, we were off the ground."),
                        TimelineItem("1799", "Sir George Cayley", "The 'Parent of Modern Aviation' realized that lift (from a fixed wing) and propulsion (to move forward) should be separate."),
                        TimelineItem("1890s", "Otto Lilienthal", "The 'Glider Man' proved humans could jump into the air and fly in a controlled way with wings."),
                        TimelineItem("1903", "The Wright Brothers", "Combined everything—a custom engine, efficient propellers, and a wing—to achieve the first controlled, powered flight.")
                    )
                ),
                LessonContentBlock.Paragraph(
                    "In just one human lifespan, we went from that first 30 mph flight at Kitty Hawk to landing a man on the moon in 1969. Today, we are even exploring **hypersonic flight**, where planes could reach the other side of the world in just two hours."
                ),
                LessonContentBlock.InfoCard(
                    title = "How Big is This Field?",
                    content = "Aerodynamics is one of the **four pillars of aerospace engineering**, alongside structures, propulsion, and flight dynamics. Its applications are almost limitless.",
                    icon = "🏗️"
                ),
                LessonContentBlock.Paragraph(
                    "Think of this stage as **mixing the colors on your palette** before you start painting a masterpiece. We aren't going to get lost in massive handbooks of data; instead, we are going to look at the 'hands of nature'—the two ways air actually grabs a plane."
                ),
                LessonContentBlock.Paragraph(
                    "### What We Will Cover in This Beginner Stage"
                ),
                LessonContentBlock.DataTable(
                    headers = listOf("Topic", "Goal"),
                    rows = listOf(
                        listOf("1. Physics Prereqs", "Learn the language of gas—pressure, density, temperature, and velocity."),
                        listOf("2. The 4 Forces", "See how Lift, Drag, Thrust, and Weight perform a delicate balancing act."),
                        listOf("3. Bernoulli", "Learn why a wing is like a lever that allows thrust to lift weight."),
                        listOf("4. Airfoil Anatomy", "Slice a wing open to look at chord line, camber, and angle of attack."),
                        listOf("5. Flow Regimes", "Explore worlds of speed—from subsonic to supersonic shockwaves."),
                        listOf("6. Tool Exploration", "Get hands-on with XFOIL to see nature's laws in action.")
                    )
                ),
                LessonContentBlock.Paragraph(
                    "By the end of this section, you won't just see a plane; you’ll see the **pressure and friction** of the air reaching out to grab it. Ready to go for a ride?"
                )
            )
        ),

        // BEGINNER MODULE 01 LESSONS
        Lesson(
            id = "b_m1_l1",
            moduleId = "b_mod1",
            lessonNumber = "Lesson 1",
            title = "The four forces of flight",
            description = "Lift, Weight, Thrust, Drag",
            duration = "10 min",
            isLocked = false,
            coreContent = listOf(
                LessonContentBlock.Paragraph(
                    "HOOK Most people believe that engines simply pull a plane forward, but thrust is actually a profound demonstration of Newton's third law of motion. An aircraft engine doesn't just 'fight' the air; it accelerates a massive amount of air backward so that the plane is propelled forward with equal and opposite force."
                ),
                LessonContentBlock.Paragraph(
                    "CONCEPT BREAKDOWN"
                ),
                LessonContentBlock.Paragraph(
                    "Thrust\n· What it is: The forward driving force that pushes the aircraft through the air, overcoming aerodynamic drag.\n· What produces it: It is generated by the propulsion system—such as a spinning propeller or a powerful jet engine—accelerating a mass of gas or air backward.\n· When it changes: Increasing thrust causes the aircraft to accelerate or climb, while decreasing it forces the aircraft to slow down or enter a glide."
                ),
                LessonContentBlock.Paragraph(
                    "Newton's Third Law (Action & Reaction)\n· What it is: For every action, there is an equal and opposite reaction.\n· Application to Flight: The 'action' is the engine accelerating and expelling a continuous mass of air out the back. The 'reaction' is the forward force (thrust) imparted directly onto the aircraft structure."
                ),
                LessonContentBlock.Paragraph(
                    "KEY INSIGHT Propulsion is entirely about mass flow. To generate a high amount of thrust, an engine can either accelerate a small amount of air to an extremely high speed (like a turbojet) or accelerate a large amount of air to a lower speed (like a high-bypass turbofan or propeller)."
                ),
                LessonContentBlock.Paragraph(
                    "REAL WORLD CONNECTION Think of the moment a heavy jet \"rotates\" its nose up during takeoff. By tilting the wings, the pilot suddenly increases the lift force until it exceeds the plane's massive weight, physically breaking the bond with the runway and rising into the sky."
                ),
                LessonContentBlock.Paragraph(
                    "COMMON MISCONCEPTION A very common misconception is that a rocket or jet engine needs 'something to push against' (like the surrounding atmospheric air or the ground) to generate thrust. In reality, thrust is purely an internal reaction force generated by expelling mass. This is why rockets can generate thrust perfectly in the vacuum of space where there is no air to push against."
                ),
                LessonContentBlock.DidYouKnow(
                    title = "The Wright Brother's Propeller",
                    content = "The Wright brothers were the first to realize that a propeller isn't just a spinning paddle—it's actually a rotating wing. By applying airfoil theory to their propeller design in 1903, they achieved an incredible 66% efficiency, which was far ahead of their time!"
                ),
                LessonContentBlock.Paragraph(
                    "SUMMARY"
                ),
                LessonContentBlock.BulletList(
                    listOf(
                        "Thrust is the forward force generated by a propulsion system to overcome aerodynamic resistance (drag).",
                        "The core physics of thrust relies completely on Newton's third law of motion: accelerating mass backward creates a forward reaction force.",
                        "Engine efficiency and thrust output depend on the balance between the mass flow rate of the air and the velocity at which it is expelled."
                    )
                )
            ),
            selfCheckList = listOf(
                SelfCheckQuestion(
                    question = "1. In a steady, level cruise, which specific force must be equal to the airplane's weight?",
                    options = listOf("Lift", "Drag", "Thrust"),
                    correctIndex = 0,
                    explanation = "As noted in the text, for a steady, stable cruise, the upward and downward forces must be exactly equal. Therefore, lift must perfectly equal weight."
                ),
                SelfCheckQuestion(
                    question = "2. What are the two primary sources of the \"drag\" force that resists a plane's forward motion?",
                    options = listOf("Air molecules hitting the front of the plane and rubbing against its \"skin\"", "Engine power and gravity", "Wing tilt and cargo weight"),
                    correctIndex = 0,
                    explanation = "According to the concept breakdown, drag arises from air molecules hitting the front of the plane and rubbing against its \"skin\"."
                ),
                SelfCheckQuestion(
                    question = "3. If an airplane's engines fail and it enters a glide, which of the four forces has been reduced to zero?",
                    options = listOf("Lift", "Weight", "Thrust"),
                    correctIndex = 2,
                    explanation = "In a descent or glide, the pilot reduces thrust to zero, and the plane's weight helps pull it forward through the air."
                )
            )
        ),
        Lesson(
            id = "b_m1_l2",
            moduleId = "b_mod1",
            lessonNumber = "Lesson 2",
            title = "Bernoulli's Principle and Continuity",
            description = "Why faster air means lower pressure",
            duration = "12 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m1_l3",
            moduleId = "b_mod1",
            lessonNumber = "Lesson 3",
            title = "Flow Regimes",
            description = "Subsonic, transonic, supersonic, and hypersonic",
            duration = "14 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m1_l4",
            moduleId = "b_mod1",
            lessonNumber = "Lesson 4",
            title = "Airfoil Anatomy",
            description = "Chord line, camber, angle of attack, and stall",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m1_l5",
            moduleId = "b_mod1",
            lessonNumber = "Lesson 5",
            title = "Aerodynamic Tool Exploration",
            description = "XFOIL hands-on — your first engineering tool",
            duration = "20 min",
            isLocked = true
        ),

        // BEGINNER MODULE 02 LESSONS
        Lesson(
            id = "b_m2_l1",
            moduleId = "b_mod2",
            lessonNumber = "Lesson 1",
            title = "Thrust Generation Physics",
            description = "Newton's third law applied to expelled mass",
            duration = "10 min",
            isLocked = false
        ),
        Lesson(
            id = "b_m2_l2",
            moduleId = "b_mod2",
            lessonNumber = "Lesson 2",
            title = "Gas Turbine Engine Anatomy",
            description = "Intake, compressor, combustor, turbine, nozzle",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m2_l3",
            moduleId = "b_mod2",
            lessonNumber = "Lesson 3",
            title = "The Brayton Cycle",
            description = "Heat at constant pressure — the engine's thermodynamic heart",
            duration = "14 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m2_l4",
            moduleId = "b_mod2",
            lessonNumber = "Lesson 4",
            title = "Rocket Propulsion Basics",
            description = "No air needed — specific impulse and staging",
            duration = "12 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m2_l5",
            moduleId = "b_mod2",
            lessonNumber = "Lesson 5",
            title = "Engine Type Comparison",
            description = "Piston, turboprop, turbojet, turbofan, rocket",
            duration = "13 min",
            isLocked = true
        ),

        // BEGINNER MODULE 03 LESSONS
        Lesson(
            id = "b_m3_l1",
            moduleId = "b_mod3",
            lessonNumber = "Lesson 1",
            title = "The Three Rotational Axes",
            description = "Roll, pitch, yaw — and what controls each",
            duration = "10 min",
            isLocked = false
        ),
        Lesson(
            id = "b_m3_l2",
            moduleId = "b_mod3",
            lessonNumber = "Lesson 2",
            title = "Control Surfaces",
            description = "Ailerons, elevators, rudder — cause and effect",
            duration = "12 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m3_l3",
            moduleId = "b_mod3",
            lessonNumber = "Lesson 3",
            title = "Static Stability",
            description = "Center of gravity vs center of pressure",
            duration = "14 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m3_l4",
            moduleId = "b_mod3",
            lessonNumber = "Lesson 4",
            title = "Orbital Mechanics Intro",
            description = "Kepler's laws and what an orbit really is",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m3_l5",
            moduleId = "b_mod3",
            lessonNumber = "Lesson 5",
            title = "Escape Velocity",
            description = "What it means to leave Earth's gravity well",
            duration = "10 min",
            isLocked = true
        ),

        // BEGINNER MODULE 04 LESSONS
        Lesson(
            id = "b_m4_l1",
            moduleId = "b_mod4",
            lessonNumber = "Lesson 1",
            title = "Stress and Strain",
            description = "Force per area, deformation per length",
            duration = "12 min",
            isLocked = false
        ),
        Lesson(
            id = "b_m4_l2",
            moduleId = "b_mod4",
            lessonNumber = "Lesson 2",
            title = "Elastic vs Plastic Deformation",
            description = "Why wings bend but do not break",
            duration = "10 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m4_l3",
            moduleId = "b_mod4",
            lessonNumber = "Lesson 3",
            title = "Aerospace Materials Comparison",
            description = "Aluminum, titanium, and carbon fiber tradeoffs",
            duration = "14 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m4_l4",
            moduleId = "b_mod4",
            lessonNumber = "Lesson 4",
            title = "Aircraft Load Types",
            description = "Bending, torsion, shear, compression in flight",
            duration = "13 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m4_l5",
            moduleId = "b_mod4",
            lessonNumber = "Lesson 5",
            title = "Thermal Challenges",
            description = "Re-entry heat and turbine blade cooling",
            duration = "15 min",
            isLocked = true
        ),

        // BEGINNER MODULE 05 LESSONS
        Lesson(
            id = "b_m5_l1",
            moduleId = "b_mod5",
            lessonNumber = "Lesson 1",
            title = "Python for Aerospace",
            description = "Plotting Cl and Cd from your XFOIL data",
            duration = "20 min",
            isLocked = false
        ),
        Lesson(
            id = "b_m5_l2",
            moduleId = "b_mod5",
            lessonNumber = "Lesson 2",
            title = "CAD Introduction",
            description = "Modeling a NACA 2412 airfoil in Fusion 360",
            duration = "25 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m5_l3",
            moduleId = "b_mod5",
            lessonNumber = "Lesson 3",
            title = "CFD Visual Exploration",
            description = "What flow visualization actually tells you",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m5_l4",
            moduleId = "b_mod5",
            lessonNumber = "Lesson 4",
            title = "NotebookLM as a Study Tool",
            description = "Organizing your aerospace resources intelligently",
            duration = "10 min",
            isLocked = true
        ),
        Lesson(
            id = "b_m5_l5",
            moduleId = "b_mod5",
            lessonNumber = "Lesson 5",
            title = "Capstone Review",
            description = "Connecting all five tools to the concepts they serve",
            duration = "15 min",
            isLocked = true
        ),

        // INTERMEDIATE MODULE 01 LESSONS
        Lesson(
            id = "i_m1_l1",
            moduleId = "i_mod1",
            lessonNumber = "Lesson 1",
            title = "Navier-Stokes Introduction",
            description = "The math formulas that govern fluid mechanics",
            duration = "12 min",
            isLocked = false
        ),
        Lesson(
            id = "i_m1_l2",
            moduleId = "i_mod1",
            lessonNumber = "Lesson 2",
            title = "Boundary Layer Development",
            description = "Laminar vs turbulent flow over curved aerodynamic bodies",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "i_m1_l3",
            moduleId = "i_mod1",
            lessonNumber = "Lesson 3",
            title = "Compressibility & Shock Waves",
            description = "Normal and oblique shocks at supersonic speeds",
            duration = "14 min",
            isLocked = true
        ),
        Lesson(
            id = "i_m1_l4",
            moduleId = "i_mod1",
            lessonNumber = "Lesson 4",
            title = "Potential Flow Theory",
            description = "Superimposing simple flows to model airfoil aerodynamics",
            duration = "16 min",
            isLocked = true
        ),
        Lesson(
            id = "i_m1_l5",
            moduleId = "i_mod1",
            lessonNumber = "Lesson 5",
            title = "Finite Wing Theory",
            description = "Lifting-line theory and induced downwash drag penalties",
            duration = "18 min",
            isLocked = true
        ),

        // INTERMEDIATE MODULE 02 LESSONS
        Lesson(
            id = "i_m2_l1",
            moduleId = "i_mod2",
            lessonNumber = "Lesson 1",
            title = "Stress Tensor & Elasticity Theory",
            description = "General 3D Hooke's Law in materials",
            duration = "14 min",
            isLocked = false
        ),
        Lesson(
            id = "i_m2_l2",
            moduleId = "i_mod2",
            lessonNumber = "Lesson 2",
            title = "Thin-Walled Structures and Buckling",
            description = "Euler buckling bounds for ribs and spars",
            duration = "15 min",
            isLocked = true
        ),
        Lesson(
            id = "i_m2_l3",
            moduleId = "i_mod2",
            lessonNumber = "Lesson 3",
            title = "Composite Classical Laminate Theory",
            description = "Stacking sequence effects on composite aircraft stiffness",
            duration = "18 min",
            isLocked = true
        ),

        // INTERMEDIATE MODULE 03 LESSONS
        Lesson(
            id = "i_m3_l1",
            moduleId = "i_mod3",
            lessonNumber = "Lesson 1",
            title = "Aircraft Performance & Cruise Range",
            description = "Breguet Range equation for turboprop and turbojet engines",
            duration = "15 min",
            isLocked = false
        ),
        Lesson(
            id = "i_m3_l2",
            moduleId = "i_mod3",
            lessonNumber = "Lesson 2",
            title = "Longitudinal Stability Modes",
            description = "Phugoid and short-period oscillations explained",
            duration = "16 min",
            isLocked = true
        ),

        // INTERMEDIATE MODULE 04 LESSONS
        Lesson(
            id = "i_m4_l1",
            moduleId = "i_mod4",
            lessonNumber = "Lesson 1",
            title = "Brayton Cycle Optimization",
            description = "Compressor pressure ratios and turbine inlet temperatures",
            duration = "16 min",
            isLocked = false
        ),
        Lesson(
            id = "i_m4_l2",
            moduleId = "i_mod4",
            lessonNumber = "Lesson 2",
            title = "Turbomachinery Velocity Triangles",
            description = "Rotor and stator blade design velocity math",
            duration = "18 min",
            isLocked = true
        ),

        // INTERMEDIATE MODULE 05 LESSONS
        Lesson(
            id = "i_m5_l1",
            moduleId = "i_mod5",
            lessonNumber = "Lesson 1",
            title = "PID Controller Design",
            description = "Closed loop feedback tuning for flight control",
            duration = "15 min",
            isLocked = false
        ),
        Lesson(
            id = "i_m5_l2",
            moduleId = "i_mod5",
            lessonNumber = "Lesson 2",
            title = "Autopilot Roll & Pitch Inner Loops",
            description = "Successive loop closure architecture for UAV controllers",
            duration = "18 min",
            isLocked = true
        )
    )

    fun getCourseById(id: String): Course? = courses.find { it.id == id }
    fun getModulesForCourse(courseId: String): List<Module> = modules.filter { it.courseId == courseId }
    fun getLessonsForModule(moduleId: String): List<Lesson> = lessons.filter { it.moduleId == moduleId }
    fun getLessonById(id: String): Lesson? = lessons.find { it.id == id }
    fun getModuleById(id: String): Module? = modules.find { it.id == id }
}

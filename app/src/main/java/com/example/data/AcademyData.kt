package com.example.data

import com.example.model.BookingSession
import com.example.model.ProgramCategory
import com.example.model.SwimProgram

object AcademyData {

    val programs: List<SwimProgram> = listOf(
        // === BABIES & INFANTS ===
        SwimProgram(
            id = "baby_01",
            title = "Infant Aquatic Sensory & Bonding",
            category = ProgramCategory.BABIES,
            ageGroup = "3 – 12 Months",
            skillLevel = "Beginner (Parent-Accompanied)",
            durationWeeks = 8,
            sessionsPerWeek = "2 sessions / week (30 mins)",
            coachRatio = "1 : 3 Parent-Baby Pairs",
            poolTemp = "32.5°C Ultra-Warm Hydrotherapy Pool",
            scheduleSummary = "Tue & Thu: 09:30 AM | Sat: 10:00 AM",
            description = "A gentle, nurturing introduction to the water environment using skin-to-skin touch, sensory water play, gentle submersion reflexes, and natural buoyancy to build lifelong water affinity.",
            highlights = listOf(
                "Gentle vestibular stimulation and floating reflex activation",
                "Warm UV-purified salt water specially adjusted for sensitive infant skin",
                "Certified Infant Aquatic Specialists with CPR/First Aid mastery",
                "Calming ambient lighting and soothing acoustics"
            ),
            curriculumModules = listOf(
                "Module 1: Water acceptance and safe cradle holds",
                "Module 2: Rhythmic cueing and gentle water pouring",
                "Module 3: Assisted back-floating and vestibular alignment",
                "Module 4: Gentle natural submersion reflex engagement"
            ),
            priceMonthly = "$220 / month",
            leadCoach = "Coach Clara Martinez",
            coachRole = "Lead Infant Aquatics Director (USSSA Certified)",
            badgeLabel = "Warm Water Sensory"
        ),
        SwimProgram(
            id = "baby_02",
            title = "Toddler Aquatic Safety & Self-Rescue",
            category = ProgramCategory.BABIES,
            ageGroup = "12 – 36 Months",
            skillLevel = "Intermediate Infant / Toddler",
            durationWeeks = 10,
            sessionsPerWeek = "2 sessions / week (30 mins)",
            coachRatio = "1 : 3 Toddlers",
            poolTemp = "32.0°C Heated Warm Pool",
            scheduleSummary = "Mon & Wed: 10:00 AM | Sun: 09:30 AM",
            description = "Vital life-saving aquatic reflexes taught through playful scenarios: rolling from face-down to a stable floating posture, edge gripping, and wall climbing.",
            highlights = listOf(
                "Roll-to-back breath recovery technique",
                "Edge orientation and two-hand poolside climb out",
                "Breath control with underwater bubble play",
                "Independent propulsion with kickboard toys"
            ),
            curriculumModules = listOf(
                "Module 1: Submersion breath holding on command",
                "Module 2: The instinctive roll-over back float",
                "Module 3: Wall turn-arounds and hand-over-hand gripping",
                "Module 4: Unassisted float hold for 15+ seconds"
            ),
            priceMonthly = "$240 / month",
            leadCoach = "Coach David Chen",
            coachRole = "Water Safety & Survival Specialist",
            badgeLabel = "Life-Saving Safety"
        ),

        // === CHILDREN & YOUTH ===
        SwimProgram(
            id = "child_01",
            title = "Junior Foundations & Streamlining",
            category = ProgramCategory.CHILDREN,
            ageGroup = "4 – 7 Years",
            skillLevel = "Level 1 – 2",
            durationWeeks = 12,
            sessionsPerWeek = "2 sessions / week (45 mins)",
            coachRatio = "1 : 4 Swimmers",
            poolTemp = "30.5°C Heated Indoor Lane",
            scheduleSummary = "Mon, Wed, Fri: 03:30 PM | Sat: 11:00 AM",
            description = "Building fearless, efficient swimmers from the ground up. Focuses on horizontal body alignment, hydrodynamic torpedo push-offs, bilateral freestyle breathing, and kick power.",
            highlights = listOf(
                "Torpedo streamline position off pool wall",
                "Bilateral rotary breathing and bubble blowing cadence",
                "Flutter kick propulsion mechanics on front and back",
                "Fun weekly badge milestone celebrations"
            ),
            curriculumModules = listOf(
                "Week 1-3: Hydrodynamic glide & balance",
                "Week 4-6: Freestyle arm recovery & kick integration",
                "Week 7-9: Backstroke continuous arm rotation",
                "Week 10-12: Deep water survival and 25m unassisted swim"
            ),
            priceMonthly = "$260 / month",
            leadCoach = "Coach Elena Rostova",
            coachRole = "Youth Development Lead (Former World Cup Swimmer)",
            badgeLabel = "Youth Core"
        ),
        SwimProgram(
            id = "child_02",
            title = "Stroke Mastery & Junior Pre-Squad",
            category = ProgramCategory.CHILDREN,
            ageGroup = "8 – 14 Years",
            skillLevel = "Level 3 – 4 (Intermediate)",
            durationWeeks = 12,
            sessionsPerWeek = "3 sessions / week (60 mins)",
            coachRatio = "1 : 6 Swimmers",
            poolTemp = "29.0°C Semi-Olympic 25m Pool",
            scheduleSummary = "Tue, Thu: 04:30 PM | Sat: 02:00 PM",
            description = "Refines all four competitive strokes (Freestyle, Backstroke, Breaststroke, and Butterfly) while introducing competition tumble turns, dive starts, and aerobic endurance sets.",
            highlights = listOf(
                "Four-stroke technique calibration and timing",
                "High-elbow freestyle catch and backstroke shoulder roll",
                "Breaststroke whip-kick biomechanics and glide rhythm",
                "Underwater dolphin kick and race flip turns"
            ),
            curriculumModules = listOf(
                "Module 1: Breaststroke glide & pull-kick-glide cycle",
                "Module 2: Butterfly undulating body kick & double arm recovery",
                "Module 3: Fast flip turns, underwater push-offs & breath management",
                "Module 4: 100m Individual Medley trial preparation"
            ),
            priceMonthly = "$290 / month",
            leadCoach = "Coach Marcus Sterling",
            coachRole = "Junior Squad Head Coach",
            badgeLabel = "Pre-Squad Elite"
        ),

        // === WOMEN SANCTUARY ===
        SwimProgram(
            id = "women_01",
            title = "Ladies Private Luxury Sanctuary",
            category = ProgramCategory.WOMEN,
            ageGroup = "16+ Years (All Levels)",
            skillLevel = "Beginner to Advanced",
            durationWeeks = 8,
            sessionsPerWeek = "2 or 3 sessions / week (50 mins)",
            coachRatio = "1 : 4 Swimmers",
            poolTemp = "31.0°C Heated Indoor Oasis",
            scheduleSummary = "Tue & Thu: 10:30 AM | Sun: 04:00 PM (Exclusive Facility Access)",
            description = "Complete privacy with female coaches and female lifeguards exclusively. Designed for women looking to learn swimming from scratch, refine their strokes, or enjoy peaceful aquatic fitness.",
            highlights = listOf(
                "100% private female-only access windows and changing suites",
                "Customized coaching pacing for confidence or fitness",
                "Low-impact resistance for posture and lymphatic circulation",
                "Complimentary herbal tea lounge and organic amenities"
            ),
            curriculumModules = listOf(
                "Track A: Water comfort & effortless breathing for beginners",
                "Track B: Graceful stroke mechanics & relaxed lap swimming",
                "Track C: Interval stamina and core toning"
            ),
            priceMonthly = "$310 / month",
            leadCoach = "Coach Sarah Al-Mansoor",
            coachRole = "Senior Women's Aquatic Specialist",
            badgeLabel = "100% Private"
        ),
        SwimProgram(
            id = "women_02",
            title = "Prenatal & Postnatal Aqua Wellness",
            category = ProgramCategory.WOMEN,
            ageGroup = "Expectant & New Mothers",
            skillLevel = "All Fitness Levels",
            durationWeeks = 6,
            sessionsPerWeek = "2 sessions / week (45 mins)",
            coachRatio = "1 : 4 Swimmers",
            poolTemp = "32.0°C Soothing Hydrotherapy Pool",
            scheduleSummary = "Mon & Wed: 11:30 AM | Sat: 09:00 AM",
            description = "Physiotherapist-approved aqua sessions utilizing the natural buoyancy of water to relieve spinal pressure, reduce joint inflammation, and strengthen pelvic floor and diaphragm muscles.",
            highlights = listOf(
                "Zero-impact weightlessness relieving lower back tension",
                "Gentle cardiovascular endurance supporting labor stamina",
                "Supervised by prenatal aquatic therapy certified coaches",
                "Specialized lymphatic drainage water movements"
            ),
            curriculumModules = listOf(
                "Module 1: Buoyancy breathing and spinal decompression",
                "Module 2: Lower extremity fluid circulation circuits",
                "Module 3: Gentle core and pelvic stabilization",
                "Module 4: Postnatal water conditioning and gentle restoration"
            ),
            priceMonthly = "$275 / month",
            leadCoach = "Dr. Maya Lindqvist, PT",
            coachRole = "Aquatic Physical Therapist",
            badgeLabel = "Maternal Wellness"
        ),

        // === MEN PERFORMANCE ===
        SwimProgram(
            id = "men_01",
            title = "Gentlemen's Aquatic Swim HIIT & Power",
            category = ProgramCategory.MEN,
            ageGroup = "18+ Years",
            skillLevel = "Intermediate to Advanced",
            durationWeeks = 8,
            sessionsPerWeek = "3 sessions / week (60 mins)",
            coachRatio = "1 : 6 Swimmers",
            poolTemp = "28.5°C Fast Olympic Water",
            scheduleSummary = "Mon, Wed, Fri: 06:30 AM (Executive Early Bird) & 07:30 PM",
            description = "High-octane aquatic intervals combining sprint swim sets with resisted drag parachutes, fin power sprints, and poolside core intervals to maximize athletic VO2 max.",
            highlights = listOf(
                "Lactate tolerance training and short rest intervals",
                "Power swim parachutes, kick paddles, and tempo trainers",
                "High-cadence VO2 max burn with zero orthopedic impact",
                "Post-workout recovery sauna and cold plunge access"
            ),
            curriculumModules = listOf(
                "Phase 1: Biomechanics tune-up and high elbow power catch",
                "Phase 2: Aerobic capacity thresholds & descending 100s",
                "Phase 3: Max velocity sprint bursts and turn explosive power",
                "Phase 4: Time trial testing & VO2 benchmark measurement"
            ),
            priceMonthly = "$320 / month",
            leadCoach = "Coach Torsten Berg",
            coachRole = "High Performance Strength & Swim Conditioning Coach",
            badgeLabel = "High Intensity"
        ),
        SwimProgram(
            id = "men_02",
            title = "Triathlon & Open-Water Endurance",
            category = ProgramCategory.MEN,
            ageGroup = "Triathletes & Marathon Swimmers",
            skillLevel = "Intermediate to Advanced",
            durationWeeks = 10,
            sessionsPerWeek = "3 sessions / week (75 mins)",
            coachRatio = "1 : 8 Swimmers",
            poolTemp = "28.0°C Olympic 50m Pool",
            scheduleSummary = "Tue, Thu: 06:00 AM | Sat: 07:00 AM (Long Aerobic Simulation)",
            description = "Engineered for triathletes preparing for Ironman 70.3, full Ironman, and open-water crossings. Master alligator sighting, drafting techniques, energy-saving 2-beat kicks, and pack navigation.",
            highlights = listOf(
                "Alligator eye sighting without breaking forward momentum",
                "Drafting off the hip and toes for up to 25% energy conservation",
                "Wetsuit swim biomechanics and rapid T1 peel-off practice",
                "Long uninterrupted threshold pyramids (800m - 3000m continuous)"
            ),
            curriculumModules = listOf(
                "Module 1: 2-beat kick efficiency and distance body alignment",
                "Module 2: Wave negotiation, turn buoy rounding, and pack pacing",
                "Module 3: Pacing discipline: avoiding early lactic spikes",
                "Module 4: 1.9km & 3.8km benchmark simulation trials"
            ),
            priceMonthly = "$340 / month",
            leadCoach = "Coach Christian Meyer",
            coachRole = "3x Ironman Finisher & Master Swim Coach",
            badgeLabel = "Triathlon Specialist"
        ),

        // === COMPETITIVE SQUADS ===
        SwimProgram(
            id = "comp_01",
            title = "The Titans Junior National Squad",
            category = ProgramCategory.COMPETITIVE,
            ageGroup = "10 – 14 Years",
            skillLevel = "State & National Qualifying Times",
            durationWeeks = 16,
            sessionsPerWeek = "5 sessions / week (90 mins)",
            coachRatio = "1 : 8 Athletes",
            poolTemp = "27.5°C FINA Olympic Standard Pool",
            scheduleSummary = "Mon - Fri: 05:00 PM - 06:30 PM | Sat: 08:00 AM",
            description = "Elite developmental squad training promising athletes for state and national qualification meets. Comprehensive periodization encompassing stroke analytics, underwater turn telemetry, and dryland flexibility.",
            highlights = listOf(
                "High-definition underwater multi-angle video stroke diagnostics",
                "Lactate threshold zone training (Zones 1-5)",
                "Official FINA electronic omega touchpads & start blocks",
                "Structured dryland flexibility and core injury prevention"
            ),
            curriculumModules = listOf(
                "Meso-Cycle 1: Base aerobic volume and stroke length precision",
                "Meso-Cycle 2: Anaerobic threshold and stroke rate cadence",
                "Meso-Cycle 3: Race-pace target splits and turn speed mechanics",
                "Meso-Cycle 4: Tapering, peak timing, and mental meet readiness"
            ),
            priceMonthly = "$390 / month",
            leadCoach = "Head Coach Viktor Kazakov",
            coachRole = "Olympic Medalist Coach & FINA Master Instructor",
            badgeLabel = "Titans Junior"
        ),
        SwimProgram(
            id = "comp_02",
            title = "Titans Elite Senior Championship Squad",
            category = ProgramCategory.COMPETITIVE,
            ageGroup = "15+ Years (National & International Level)",
            skillLevel = "Elite Competitive Standard",
            durationWeeks = 20,
            sessionsPerWeek = "8 - 10 sessions / week (Includes double days)",
            coachRatio = "1 : 6 Athletes",
            poolTemp = "27.0°C Competition Regulation 50m Pool",
            scheduleSummary = "Mon - Sat: 05:30 AM (Double) & 04:30 PM",
            description = "Our flagship competitive tier. Designed for Olympic trials, national podium contenders, and NCAA Division 1 hopefuls. Combines bio-energetic training, speed-assisted towing, and sports science support.",
            highlights = listOf(
                "Full sports science support: lactate testing, biomechanics, recovery",
                "Customized periodization program for individual target meets",
                "High-speed velocity tethering and resistance tower training",
                "Comprehensive weight room Olympic lifting and dryland power"
            ),
            curriculumModules = listOf(
                "Phase 1: Volume endurance & stroke kinetic efficiency",
                "Phase 2: Specific race pace & stroke index optimization",
                "Phase 3: High-intensity anaerobic power & reaction start times",
                "Phase 4: Scientific taper and championship execution"
            ),
            priceMonthly = "$480 / month",
            leadCoach = "Head Coach Viktor Kazakov & Staff",
            coachRole = "Director of High Performance Aquatics",
            badgeLabel = "Elite National"
        )
    )

    val sampleBookings: List<BookingSession> = listOf(
        BookingSession(
            id = "bk_001",
            programId = "comp_01",
            programTitle = "The Titans Junior National Squad",
            category = ProgramCategory.COMPETITIVE,
            date = "Tomorrow, 05:00 PM",
            timeSlot = "05:00 PM - 06:30 PM",
            laneNumber = 4,
            coachName = "Viktor Kazakov",
            swimmerName = "Alexander Vance",
            notes = "Focus on 100m Butterfly turn transitions and underwater breakout kick count.",
            status = "Confirmed"
        ),
        BookingSession(
            id = "bk_002",
            programId = "men_01",
            programTitle = "Gentlemen's Aquatic Swim HIIT & Power",
            category = ProgramCategory.MEN,
            date = "Thursday, 06:30 AM",
            timeSlot = "06:30 AM - 07:30 AM",
            laneNumber = 2,
            coachName = "Torsten Berg",
            swimmerName = "Alexander Vance",
            notes = "VO2 Max threshold intervals with parachute resistance.",
            status = "Confirmed"
        )
    )

    val quickQuestions = listOf(
        "How can I fix dropped hips and lower body sink during Freestyle?",
        "Generate a 45-minute sprint drill set for 50m competition taper",
        "What are the best gentle water exercises for baby water comfort?",
        "How do triathletes sight effectively in open water without neck fatigue?",
        "Explain the timing coordination between Butterfly pull and two dolphin kicks"
    )
}
